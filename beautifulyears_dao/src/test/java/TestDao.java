
import com.feizuo.dao.SetmealDao;
import com.feizuo.pojo.Setmeal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext-dao.xml" )
@RunWith(value = SpringJUnit4ClassRunner.class)
public class TestDao {

    @Autowired
    private SetmealDao setmealDao;

    @Test
    public void test1(){
        Setmeal setmealById = setmealDao.findSetmealById(3);
        System.out.println("setmealById = " + setmealById);
    }
}
