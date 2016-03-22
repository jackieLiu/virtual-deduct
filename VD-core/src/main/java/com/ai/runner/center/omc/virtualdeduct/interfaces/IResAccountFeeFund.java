package com.ai.runner.center.omc.virtualdeduct.interfaces;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;

public interface IResAccountFeeFund extends IAccountFeeFund{
    void addFundResBook(Long bookid,FundResBook resBook);
}
