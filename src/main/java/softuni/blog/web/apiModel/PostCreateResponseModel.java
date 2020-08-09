package softuni.blog.web.apiModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostCreateResponseModel {

    private String title;
    private String summary;
    private LocalDateTime published;
    private String content;
    private String author;
    private String category;
    private String url;

    public PostCreateResponseModel() {
    }

    public PostCreateResponseModel(String title, String summary, LocalDateTime published, String content, String author, String category, String url) {
        this.title = title;
        this.summary = summary;
        this.published = published;
        this.content = content;
        this.author = author;
        this.category = category;
        this.url = url;
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


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
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

    @NotNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NotNull
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
