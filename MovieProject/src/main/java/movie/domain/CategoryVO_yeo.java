package movie.domain;

public class CategoryVO_yeo  {

	// === Field === //
	private String category_code; // 장르코드
	private String category;      // 장르
	
	
	// === Method === //
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
