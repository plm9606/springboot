package com.example.web.comunityweb.domain.enums;

public enum BoardType {
    notice("공지사항"),
    free("자유게시판");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    BoardType(String value) {
        this.value = value;
    }
}
