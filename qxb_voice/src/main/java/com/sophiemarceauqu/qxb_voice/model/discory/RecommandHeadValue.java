package com.sophiemarceauqu.qxb_voice.model.discory;

import com.sophiemarceauqu.qxb_voice.model.BaseModel;

import java.util.ArrayList;

public class RecommandHeadValue extends BaseModel {
    public ArrayList<String > ads;
    public ArrayList<RecommandMiddleValue> middle;
    public ArrayList<RecommandFooterValue> footer;
}
