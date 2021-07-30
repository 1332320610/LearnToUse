package staticProxy;

public class UserDaoProxy implements UserDao{

    private UserDaoImpl userDao;

    public UserDaoProxy(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("开始保存");
        userDao.save();
        System.out.println("保存完成");
    }
}
