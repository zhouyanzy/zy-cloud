package top.zhouy.shophome.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import top.zhouy.shophome.bean.type.ImgType;
import top.zhouy.shophome.bean.type.ModelType;
import top.zhouy.shophome.bean.type.UrlType;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 首页模块
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
public class ShopHomeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块类型
     */
    private ModelType modelType;

    /**
     * 图片类型
     */
    private ImgType imgType;

    /**
     * 原图
     */
    private String oldImg;

    /**
     * 图片
     */
    private String img;

    /**
     * 跳转类型
     */
    private UrlType urlType;

    /**
     * 跳转地址
     */
    private String urlKey;

    /**
     * 开始时间
     */
    private LocalDateTime startAt;

    /**
     * 结束时间
     */
    private LocalDateTime endAt;

    /**
     * 是否删除，1是
     */
    private Boolean archive;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public ImgType getImgType() {
        return imgType;
    }

    public void setImgType(ImgType imgType) {
        this.imgType = imgType;
    }

    public String getOldImg() {
        return oldImg;
    }

    public void setOldImg(String oldImg) {
        this.oldImg = oldImg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ShopHomeModel{" +
        "id=" + id +
        ", modelType=" + modelType +
        ", imgType=" + imgType +
        ", oldImg=" + oldImg +
        ", img=" + img +
        ", urlType=" + urlType +
        ", urlKey=" + urlKey +
        ", startAt=" + startAt +
        ", endAt=" + endAt +
        ", archive=" + archive +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
