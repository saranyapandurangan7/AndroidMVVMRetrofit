package com.saranya.androidmvvm.fragment;

import com.saranya.androidmvvm.model.UserContentResponse;

import java.util.List;

@SuppressWarnings("unused")
interface NewsFragmentView {
    void showProgressIndication();

    void hideProgressIndication();

    void bindListData(List<UserContentResponse.Contents> contents);
}
