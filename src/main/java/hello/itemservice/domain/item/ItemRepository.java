package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    // 멀티쓰레드 환경에서 동시에 store 접근하는 경우에는 hashmap 사용 X -> concurrent hashmap
    private static final Map<Long, Item> store = new HashMap<>();
    // 동시에 사용하면 값이 꼬일 수 있기 때문에 long -> atomic long
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    // 한번 감싸서 반환하는 이유는 ArrayList 에 값을 넣어도 실제 store 에는 변함이 없다.
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}