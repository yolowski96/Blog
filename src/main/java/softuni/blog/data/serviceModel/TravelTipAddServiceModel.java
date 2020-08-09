package softuni.blog.data.serviceModel;

import org.hibernate.validator.constraints.Length;
import softuni.blog.data.entity.User;

import javax.validation.constraints.NotNull;

public class TravelTipAddServiceModel extends BaseServiceModel{

    private String title;
    private String description;
    private User author;

    public TravelTipAddServiceModel() {
    }

    @NotNull
    @Length(min = 2,max = 40,message = "Title length must be between 2 and 40 characters!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Length(min = 2,message = "Description length must be minimum 2 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
