package com.example.springclouddemo.util;

import java.util.List;

public class NewsMessage extends TextMessageBase {
    private int articleCount;
    private List<News> article;

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<News> getArticle() {
        return article;
    }

    public void setArticle(List<News> article) {
        this.article = article;
    }
}
