package springadvanced.exam.utils.baseClasses;

public abstract class BaseDto {

    private String id;

    public BaseDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
