import com.ydawork1.dao.CategoryDao;
import com.ydawork1.dao.CategoryDaoImpl;
import com.ydawork1.dao.UserDao;
import com.ydawork1.dao.UserDaoImpl;
import com.ydawork1.model.Category;
import com.ydawork1.model.User;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl(User.class);
        CategoryDao categoryDao = new CategoryDaoImpl(Category.class);

        User admin = new User( 1L, "Dima", "yda@email.com", "6531");
        User user = new User(2l, "Oleg", "test@test", "test");
        Category tv = new Category(1L, "sony", "you can watch it");
        Category moto = new Category(2L, "moto", "you can fly with it");
        Category car = new Category(3L, "bmw", "too expensive to maintain");

        userDao.save(admin);
        userDao.save(user);
        categoryDao.save(tv);
        categoryDao.save(moto);
        categoryDao.save(car);

        admin.setPassword("7777");
        moto.setName("harley davidson");

        userDao.update(admin);
        categoryDao.update(moto);

        User userFromDB = userDao.get(1L).get();
        Category categoryFromDB = categoryDao.get(2L).get();

        System.out.println(userFromDB);
        System.out.println(categoryFromDB);

        userDao.delete(2L);
        categoryDao.delete(1L);

        List<User> users = userDao.getAll();
        List<Category> categories = categoryDao.getAll();
    }
}
