package com.astayc.ebankiboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class EBankiBoot {

    private @Id
    @GeneratedValue Long id ;

    private String bankname;
    private String bankaddress;

    public EBankiBoot() {
    }

    public EBankiBoot(String bankname, String bankaddress) {
        this.bankname = bankname;
        this.bankaddress = bankaddress;
    }

    public Long getId() {
        return id;
    }

    public String getBankname() {
        return bankname;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }
}
