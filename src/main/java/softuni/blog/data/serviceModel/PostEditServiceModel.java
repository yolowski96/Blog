package softuni.blog.data.serviceModel;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostEditServiceModel extends BaseServiceModel{

    private String summary;
    private LocalDateTime published;
    private String content;
    private String url;

    public PostEditServiceModel() {
    }

    @NotNull
    @Length(min = 2,max = 50,message = "Summary length must be between 2 and 50 characters!")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
