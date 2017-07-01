package ru.alexgoodman.usermanager.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Paging {
    public static final int DEFAULT_LINE_OF_PAGE = 5; //Значение колличества отображаемых строк по умолчанию
    private int lineOfPage = DEFAULT_LINE_OF_PAGE;

    public void setLineOfPage(int lineOfPage) {
        this.lineOfPage = lineOfPage;
    }

    public int getLineOfPage() {

        return lineOfPage;
    }
}
