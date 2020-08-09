package softuni.blog.web.apiModel;

public class CategoryResponseModel {
    private String name;
    private String content;

    public CategoryResponseModel() {
    }

    public CategoryResponseModel(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
