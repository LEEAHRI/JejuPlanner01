package kosta.mvc.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kosta.mvc.domain.GoodsLine;
import kosta.mvc.repository.GoodsLineRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsLineServiceImpl implements GoodsLineService {

	private final GoodsLineRepository goodsLineRep;
	
	@Override
	public void goodsLineCart(GoodsLine goodsLine) {
		
		goodsLineRep.save(goodsLine);
		
	}

}