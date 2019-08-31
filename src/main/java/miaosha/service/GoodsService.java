package miaosha.service;

import java.util.List;

import miaosha.vo.goods.GoodsVo;

public interface GoodsService {

	/**
	 * 查询商品信息，包含秒杀的商品
	 * @return
	 */
	public List<GoodsVo> listGoods();
	
	public GoodsVo getGoodsVoByGoodsId(Long goodsId);
	
	/**
	 * 减库存
	 * @param goods
	 */
	public void reduceStock(GoodsVo goods);
}
