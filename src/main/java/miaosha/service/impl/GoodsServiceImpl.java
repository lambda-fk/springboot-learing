package miaosha.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miaosha.dao.GoodsDao;
import miaosha.dao.domain.MiaoshaGoods;
import miaosha.service.GoodsService;
import miaosha.vo.goods.GoodsVo;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	GoodsDao goodsDao ;

	public List<GoodsVo> listGoods() {
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}

}
