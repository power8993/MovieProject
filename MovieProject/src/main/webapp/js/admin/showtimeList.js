
$(document).ready(function(){
	

});// end of $(document).ready(function(){})------------------------------



// === 영화 검색에 따른 상영시간이 등록된 영화 목록을 보여주는 이벤트 처리
function goSearch() {
	
	//const search_time = $("select[name='search_time']").val();
	//const search_movie_title = $("input:text[name='search_movie_title']").val().trim();

	const frm = document.showtime_search_frm
	// frm.action = "showtimeList.mp";
	// frm.method = "get";
	
	frm.submit();

}// end of function goSearch() {}-------------------------------------------