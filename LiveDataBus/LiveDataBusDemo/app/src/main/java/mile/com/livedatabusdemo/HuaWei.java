package mile.com.livedatabusdemo;

/**
 * Created by mile on 2019/4/25.
 */

public class HuaWei {
    private String name;
    private String type;

    public HuaWei(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
