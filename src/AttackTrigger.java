import java.nio.channels.Pipe;

public interface AttackTrigger {

  public int damageValue();

  public void getHurts(int damage);

}
