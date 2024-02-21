package br.com.sandes.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BooksVO extends RepresentationModel<BooksVO> implements Serializable {
        private static final long serialVersionUID = 1L;

        @JsonProperty("id")
        private Long key;
        private String author;
        private Double price;
        private Date launchDate;
        private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BooksVO booksVO = (BooksVO) o;

        if (!Objects.equals(key, booksVO.key)) return false;
        if (!Objects.equals(author, booksVO.author)) return false;
        if (!Objects.equals(price, booksVO.price)) return false;
        if (!Objects.equals(launchDate, booksVO.launchDate)) return false;
        return Objects.equals(title, booksVO.title);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (launchDate != null ? launchDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
