package com.faturacao.crm.controller;

import com.faturacao.crm.model.Banner;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class BannerController implements Serializable {

    private List<Banner> banners;
    private Banner bannerForm;

    @PostConstruct
    public void init() {
        banners = new ArrayList<>();
        bannerForm = new Banner();
    }

    public void salvar() {
        banners.add(bannerForm);
        bannerForm = new Banner();
    }

    public List<Banner> getBanners() { return banners; }
    public Banner getBannerForm() { return bannerForm; }
    public void setBannerForm(Banner bannerForm) { this.bannerForm = bannerForm; }
}
