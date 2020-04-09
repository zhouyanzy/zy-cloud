package top.zhouy.shoporder.bean.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import top.zhouy.shoporder.bean.type.OrderStatus;
import top.zhouy.shoporder.bean.type.OrderType;
import top.zhouy.shoporder.bean.type.PayType;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
public class ShopOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 订单总额
     */
    private BigDecimal totalFee;

    /**
     * 商品金额
     */
    private BigDecimal goodsFee;

    /**
     * 折扣金额
     */
    private BigDecimal discountFee;

    /**
     * 剩余金额
     */
    private BigDecimal laveFee;

    /**
     * 订单类型
     */
    private OrderType orderType;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 支付时间
     */
    private LocalDateTime paidAt;

    /**
     * 支付金额
     */
    private BigDecimal paidFee;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getGoodsFee() {
        return goodsFee;
    }

    public void setGoodsFee(BigDecimal goodsFee) {
        this.goodsFee = goodsFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getLaveFee() {
        return laveFee;
    }

    public void setLaveFee(BigDecimal laveFee) {
        this.laveFee = laveFee;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public PayType getPayType() {
        return payType;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public BigDecimal getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
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
        return "ShopOrder{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", buyerId=" + buyerId +
        ", totalFee=" + totalFee +
        ", goodsFee=" + goodsFee +
        ", discountFee=" + discountFee +
        ", laveFee=" + laveFee +
        ", orderType=" + orderType +
        ", orderStatus=" + orderStatus +
        ", payNo=" + payNo +
        ", payType=" + payType +
        ", paidAt=" + paidAt +
        ", paidFee=" + paidFee +
        ", archive=" + archive +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
