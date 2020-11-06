package top.zhouy.bloghome.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.zhouy.bloghome.bean.status.ArticleStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 博客
 * </p>
 *
 * @author zhouy
 * @since 2020-03-18
 */
@Data
public class BlogArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Boolean archive;

    private Date createdAt;

    private Date updatedAt;

    @TableField(value = "status")
    private ArticleStatus status;

    private String description;

    private Long comments;

    private Long likes;

    private Long views;

    private String img;

    private Long numbers;
}
