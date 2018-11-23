package kr.or.ddit;

public class paggingCalculate {
	public static void main(String[] args) {
		int totalRecord = 101;
		int srcreenSize = 10;
		int blockSize = 5;
		int currentPage = 11;
		
		//총페이지수구하기
		int totalPage = 0;
		if (!(totalRecord % srcreenSize == 0)) {
			totalPage = totalRecord / srcreenSize +1;
		}else {
			totalPage = totalRecord / srcreenSize;
		}
		System.out.println(totalPage);
		
		//시작게시물번호와 종료게시물번호구하기
		int startRow = 0;
		int endRow = 0;
		startRow = (currentPage*srcreenSize)-(srcreenSize-1);
		endRow = currentPage*srcreenSize;
		System.out.println(startRow);
		System.out.println(endRow);
		
		
		startRow = (currentPage-1)*srcreenSize+1;
		System.out.println(startRow);
		
		int startPage = 0;
		int endPage = 0;
		
		startPage = (currentPage-1)/blockSize *blockSize+1;
		endPage = startRow + (blockSize -1);
		System.out.println(startPage);
		System.out.println(endPage);
		
	}
}
