package com.alex.code.foundation.data.preference;

public interface IPreference {
    // 按照一下格式进行
    // KEY
    // set
    // get

    String IS_FIRST_RUN = "is_first_run";
    String TOKEN = "token";
    String SEARCH_HISTORY = "search_history";

    void setIsFirstRun(boolean isFirstRun);

    boolean isFirstRun();

    void setToken(String token);

    String getToken();

    void setSearchHistory(String searchHistory);

    String getSearchHistory();
}
