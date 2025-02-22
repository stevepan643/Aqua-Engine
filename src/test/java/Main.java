import com.steve.manager.Application;
import com.steve.manager.Configuration;
import com.steve.manager.ObjectManager;
import com.steve.util.LogUtil;
import com.steve.util.ObjUtil;
import org.slf4j.Logger;

public class Main extends Application {

  Logger LOGGER = LogUtil.getLogger();

  @Override
  protected void config(Configuration conf) {}

  @Override
  protected void preRun() {
    LOGGER.info("Hello");

    ObjectManager.addObject(ObjUtil.loadModel("model/cube.obj"));
  }

  @Override
  protected void process() {
    // ObjectManager.render();
  }

  public static void main(String[] args) {
    launch(new Main());
  }
}
