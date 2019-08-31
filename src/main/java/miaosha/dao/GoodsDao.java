package miaosha.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import miaosha.dao.domain.MiaoshaGoods;
import miaosha.vo.goods.GoodsVo;

@Mapper
public interface GoodsDao {
	
	/**
	 * 查询2张表的信息使用  GoodsVo来封装2张表的数据
	 * @return
	 */
	@Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();

	/**
	 * 
	 * @param goodsId
	 * @return
	 */
	@Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
	public int reduceStock(MiaoshaGoods g);
	
}
