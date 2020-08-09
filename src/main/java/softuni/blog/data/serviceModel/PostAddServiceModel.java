package softuni.blog.data.serviceModel;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import softuni.blog.data.entity.Category;
import softuni.blog.data.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostAddServiceModel extends BaseServiceModel {

    private String title;
    private String summary;
    private LocalDateTime published;
    private String content;
    private User author;
    private Category category;
    private String url;

    public PostAddServiceModel() {
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
    @Length(min = 2,max = 50,message = "Summary length must be between 2 and 50 characters!")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    @NotNull
    @Length(min = 2,message = "Content length must be minimum 2 characters!")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
