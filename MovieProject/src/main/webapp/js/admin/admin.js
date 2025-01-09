
$(document).ready(function(){
	
	// 페이지 로드 시 7일간 전체 예매 현황 차트 클릭
	setTimeout(function() {$("button#day_btn").trigger('click'); }, 100);  // 페이지 로드가 끝난 뒤 0.1초 후 클릭 이벤트 실행

	
	
	// === 상단 방문자수/매출액 데이터 가져오기 시작 == //
	setTimeout(function() { 
		$.ajax({
			url: 'adminChart.mp',
			type: 'get',
			data: {"data":'statistics'},
			async: true,
			success: function(json) {
				// console.log(json);
				
				$("div#today_visit_cnt").text(json.today_visit_cnt.toLocaleString()+" 명");
				$("div#total_visit_cnt").text(json.total_visit_cnt.toLocaleString()+" 명");
				$("div#today_pay_sum").text(json.today_pay_sum.toLocaleString()+" 원");
				$("div#total_pay_sum").text(json.total_pay_sum.toLocaleString()+" 원");
			},
			error: function() {
	            alert("방문자수/매출액 데이터를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
		});// end of $.ajax(function{})----------
	}, 300);
	// === 상단 방문자수/매출액 데이터 가져오기 끝  == //
	
	///////////////////////////////////////////////////////////////
	
	// ==== 오늘의 영화 예매 현황(파이차트) 시작 ==== // 
	setTimeout(function() {
		$.ajax({
			url: 'adminChart.mp',
			type: 'get',
			data: {"data":'pie'},
			async: true,
			
			success: function(json) {
			//console.log(json);
			
			
				// 라벨
				const movie_title = [];
				const reserved_cnt = [];
				
				json.forEach(item => {
					movie_title.push(item.movie_title);
					reserved_cnt.push(item.reserved_cnt);
				});
				
				
				// 차트를 넣을 위치
				const today_reserved_chart = $("canvas#today_reserved_chart")[0].getContext('2d');
				new Chart(today_reserved_chart, {
				    type: 'pie',
				    data: {
				        labels: movie_title,
				        datasets: [{
				            label: '예매건수',
				            data: reserved_cnt,
				            backgroundColor: ['#ffe268', '#2e313d', '#b46358',  '#16A085', '#e9a441', '762f1c',
											  '#e1d3c0', '#8db880', '#c5a16f', '#453b25', '#6e6342']
				        }]
				    }
				});
				
			},
			error: function() {
	            alert("오늘의 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
		});// end of $.ajax({})--------------------------------------
	}, 500);
	// ==== 오늘의 영화 예매 현황(파이차트) 끝  ==== // 
	
	
	
	// ==== 7일간 매출 현황(막대차트) 시작 ==== // 
	setTimeout(function() {
		$.ajax({
			url: 'adminChart.mp',
			type: 'get',
			data: {"data":'bar'},
			async: true,
			
			success: function(json) {
			//console.log(json);
				
				// 라벨
				const pay_date = [];
				const pay_sum = [];
				
				json.forEach(item => {
					pay_date.push(item.pay_date.substring(5,7)+"/"+item.pay_date.substring(8,10));
					pay_sum.push(item.pay_sum);
				});
				
				
				// 차트를 넣을 위치
				const today_payment_chart = $("canvas#today_payment_chart")[0].getContext('2d');
				new Chart(today_payment_chart, {
				    type: 'bar',
				    data: {
				        labels: pay_date,
				        datasets: [{
				            label: '매출액',
				            data: pay_sum,
							backgroundColor: '#C94F5333',
							borderColor: '#C94F53',
							borderWidth: 1
				        }]
					},
					options: {
			            scales: {
			              y: {
			                beginAtZero: true
			              }
			            }
		          	}
				});
			},
			error: function() {
	            alert("7일간 매출 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
		});// end of $.ajax({})--------------------------------------
	}, 700);
	// ==== 7일간 매출 현황(막대차트) 끝  ==== // 
		
	
	
	// ==== 버튼마다 보여주는 차트를 다르게끔 처리해주는 이벤트 시작 ==== //
	let exist_chart = null;  // 기존 차트를 제거하는 .destroy() 메소드를 사용하기 위한 변수
	const total_reserved_chart = $("canvas#total_reserved_chart")[0].getContext('2d');
	
	  ////////////////////////////////////
	 // **** 7일간 전체 예매 현황 차트 **** //
	////////////////////////////////////
	$("button#day_btn").click(function(e) {
		
		// 클릭된 버튼에 'btn-clicked' 클래스 추가
        $(this).addClass("btn-clicked");

        // 클릭되지 않은 다른 버튼에서 'btn-clicked' 클래스 제거
        $("button").not(this).removeClass("btn-clicked");
		
		$.ajax({
			url: 'adminChart.mp',
			type: 'get',
			data: {"data":'line',
				   "range":'day'
			},
			async: true,
			
			success: function(json) {
			//console.log(json);
			
				// 라벨
				const day_pay_date = [];
				const day_pay_sum = [];
				const day_reserved_cnt = [];
				
				json.forEach(item => {
					day_pay_date.push(item.day_pay_date.substring(0,10));
					day_pay_sum.push(item.day_pay_sum);
					day_reserved_cnt.push(item.day_reserved_cnt);
				});
				
				// 기존 차트가 존재하면 삭제
				if (exist_chart) {
				    exist_chart.destroy(); 
				}
				
				// 차트를 넣을 위치
				exist_chart = new Chart(total_reserved_chart, {
				    type: 'line',

					data: {
						labels: day_pay_date,
						datasets: [{
							label: '지난 7일간 예매 현황',
							data: day_reserved_cnt,
							borderWidth: 2,			 // 얇은 선
							borderColor: '#0c666e',  // 라인 색
							pointBackgroundColor: '#eb5e28', // 꼭지점 동그라미 색상
							pointRadius: 3, // 동그라미 크기
				            fill: true,    // 영역 채우기 여부
							backgroundColor: '#0c666e33',  // 영역 색상
				            tension: 0.1  	// 선의 곡률
						}]
					},
					options: {
						responsive: true,
				        scales: {
				            x: {
				                title: {
				                    display: true,
				                    //text: '날짜'
								}
							},
							y: {
								title: {
									display: true,
									//text: '예매 건수'
								},
								min: 0,  // Y축의 최소값을 0으로 설정
								ticks: {
									stepSize: 1, // Y축 값을 정수로 표시 (1씩 증가)
									beginAtZero: true,  // Y축이 0부터 시작하도록 설정
									callback: function(value) {
										// 값이 정수일 경우에만 표시하고, 소수점은 표시하지 않음
										return Number.isInteger(value) ? value : '';
									}
								}
							}
						},
						plugins: {
							tooltip: {
								callbacks: {
									title: function(tooltipItem) {
										return tooltipItem[0].label;  // 날짜 (mm-dd)
									},
									label: function(tooltipItem) {
										// 결제 금액 표시
										const dateIndex = tooltipItem.dataIndex;  // 마우스를 올린 날짜의 인덱스
										const payAmount = day_pay_sum[dateIndex];     // 해당 날짜의 결제 금액
										return '결제 금액: ' + payAmount.toLocaleString() + ' 원';    // 결제 금액만 툴팁에 표시
									}
								}
							}
						}
					}
				});// end of new Chart(reserved_chart, {})--------------------------------
			},
			error: function() {
	            	alert("7일간 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        	}
			});// end of $.ajax({})--------------------------------------
			
	});// end of $("button#day_btn").click((e) => {})-------------------------
	
	
	
	  ////////////////////////////////////
	 // **** 30일간 전체 예매 현황 차트 **** //
	////////////////////////////////////
	$("button#month_btn").click(function(e) {
			
		// 클릭된 버튼에 'btn-clicked' 클래스 추가
        $(this).addClass("btn-clicked");

        // 클릭되지 않은 다른 버튼에서 'btn-clicked' 클래스 제거
        $("button").not(this).removeClass("btn-clicked");
		
	    $.ajax({
	        url: 'adminChart.mp',
	        type: 'get',
	        data: {
	            "data": 'line',
	            "range": 'month'
	        },
	        async: true,
	        success: function(json) {
	            //console.log(json);
				
	            // 데이터를 받아와서 차트를 업데이트
	            const month_pay_date = [];
	            const month_pay_sum = [];
	            const month_reserved_cnt = [];
	            
	            json.forEach(item => {
	                month_pay_date.push(item.month_pay_date.substring(5,7)+"/"+item.month_pay_date.substring(8,10));
	                month_pay_sum.push(item.month_pay_sum);
	                month_reserved_cnt.push(item.month_reserved_cnt);
	            });

				// 기존 차트가 존재하면 삭제
                if (exist_chart) {
                    exist_chart.destroy(); 
                }
								
	            // 새로운 차트 그리기
	            exist_chart = new Chart(total_reserved_chart, {
	                type: 'line',
	                data: {
	                    labels: month_pay_date,
	                    datasets: [{
	                        label: '지난 30일간 예매 현황',
	                        data: month_reserved_cnt,
							borderWidth: 2,			 // 얇은 선
							borderColor: '#0c666e',  // 라인 색
							pointBackgroundColor: '#eb5e28', // 꼭지점 동그라미 색상
							pointRadius: 3, // 동그라미 크기
				            fill: true,    // 영역 채우기 여부
							backgroundColor: '#0c666e33',  // 영역 색상
				            tension: 0.1  	// 선의 곡률
	                    }]
	                },
	                options: {
	                    responsive: true,
	                    scales: {
	                        x: {
	                            title: {
	                                display: true,
	                                //text: '날짜'
	                            }
	                        },
	                        y: {
	                            title: {
	                                display: true,
	                                //text: '예매 건수'
	                            },
	                            min: 0,  // Y축의 최소값을 0으로 설정
	                            ticks: {
	                                stepSize: 1,  // Y축 값을 정수로 표시 (1씩 증가)
	                                beginAtZero: true,  // Y축이 0부터 시작하도록 설정
	                                callback: function(value) {
	                                    return Number.isInteger(value) ? value : '';  // 정수만 표시
	                                }
	                            }
	                        }
	                    },
	                    plugins: {
	                        tooltip: {
	                            callbacks: {
	                                title: function(tooltipItem) {
	                                    return tooltipItem[0].label;  // 날짜 (mm-dd)
	                                },
	                                label: function(tooltipItem) {
	                                    const dateIndex = tooltipItem.dataIndex;
	                                    const payAmount = month_pay_sum[dateIndex];
	                                    return '결제 금액: ' + payAmount.toLocaleString() + ' 원';
	                                }
	                            }
	                        }
	                    }
	                }
	            });

	            // 새로운 차트를 보이게 하기
	            $("canvas#total_reserved_chart").show();
	        },
	        error: function() {
	            alert("30일간 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
			
	    });// end of $.ajax({})--------------------------------------
		
	});// end of $("button#month_btn").click((e) => {})-------------------------
	
	
	
	  //////////////////////////////////
	 // **** 연간 전체 예매 현황 차트 **** //
	//////////////////////////////////
	$("button#year_btn").click(	function(e) {
			
		// 클릭된 버튼에 'btn-clicked' 클래스 추가
        $(this).addClass("btn-clicked");

        // 클릭되지 않은 다른 버튼에서 'btn-clicked' 클래스 제거
        $("button").not(this).removeClass("btn-clicked");
		
	    $.ajax({
	        url: 'adminChart.mp',
	        type: 'get',
	        data: {
	            "data": 'line',
	            "range": 'year'
	        },
	        async: true,
	        success: function(json) {
	            //console.log(json);
				
	            // 데이터를 받아와서 차트를 업데이트
	            const year_pay_date = [];
	            const year_pay_sum = [];
	            const year_reserved_cnt = [];
	            
	            json.forEach(item => {
	                year_pay_date.push(item.year_pay_date);
	                year_pay_sum.push(item.year_pay_sum);
	                year_reserved_cnt.push(item.year_reserved_cnt);
	            });

				// 기존 차트가 존재하면 삭제
	            if (exist_chart) {
	                exist_chart.destroy(); 
	            }
								
	            // 새로운 차트 그리기
	            exist_chart = new Chart(total_reserved_chart, {
	                type: 'line',
	                data: {
	                    labels: year_pay_date,
	                    datasets: [{
	                        label: '지난 1년간 예매 현황',
	                        data: year_reserved_cnt,
							borderWidth: 2,			 // 얇은 선
							borderColor: '#0c666e',  // 라인 색
							pointBackgroundColor: '#eb5e28', // 꼭지점 동그라미 색상
							pointRadius: 3, // 동그라미 크기
				            fill: true,    // 영역 채우기 여부
							backgroundColor: '#0c666e33',  // 영역 색상
				            tension: 0.1  	// 선의 곡률
	                    }]
	                },
	                options: {
	                    responsive: true,
	                    scales: {
	                        x: {
	                            title: {
	                                display: true,
	                                //text: '날짜'
	                            }
	                        },
	                        y: {
	                            title: {
	                                display: true,
	                                //text: '예매 건수'
	                            },
	                            min: 0,  // Y축의 최소값을 0으로 설정
	                            ticks: {
	                                stepSize: 1,  // Y축 값을 정수로 표시 (1씩 증가)
	                                beginAtZero: true,  // Y축이 0부터 시작하도록 설정
	                                callback: function(value) {
	                                    return Number.isInteger(value) ? value : '';  // 정수만 표시
	                                }
	                            }
	                        }
	                    },
	                    plugins: {
	                        tooltip: {
	                            callbacks: {
	                                title: function(tooltipItem) {
	                                    return tooltipItem[0].label;  // 날짜 (mm-dd)
	                                },
	                                label: function(tooltipItem) {
	                                    const dateIndex = tooltipItem.dataIndex;
	                                    const payAmount = year_pay_sum[dateIndex];
	                                    return '결제 금액: ' + payAmount.toLocaleString() + ' 원';
	                                }
	                            }
	                        }
	                    }
	                }
	            });

	            // 새로운 차트를 보이게 하기
	            $("canvas#total_reserved_chart").show();
	        },
	        error: function() {
	            alert("연 간 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
			
	    });// end of $.ajax({})--------------------------------------
		
	});// end of $("button#month_btn").click((e) => {})-------------------------
	
	// ==== 버튼마다 보여주는 차트를 다르게끔 처리해주는 이벤트 끝  ==== //

	
	
	// ==== 방문자 수 차트 시작 ==== // 
	setTimeout(function() {
		$.ajax({
	        url: 'adminChart.mp',
	        type: 'get',
	        data: {"data":'area'},
	        async: true,
	        success: function(json) {
	            //console.log(json);
				
	            // 데이터를 받아와서 차트를 업데이트
	            const visit_date = [];
	            const visited_cnt = [];
	            
	            json.forEach(item => {
	                visit_date.push(item.visit_date.substring(0,10));
	                visited_cnt.push(item.visited_cnt);
	            });
								
				// 차트를 넣을 위치
				const total_visitors_chart = $("canvas#total_visitors_chart")[0].getContext('2d');
	            new Chart(total_visitors_chart, {
	                type: 'line',
	                data: {
	                    labels: visit_date,
	                    datasets: [{
	                        label: '일일 접속자 수',
	                        data: visited_cnt,
							borderWidth: 2,			 // 얇은 선
							borderColor: '#CCC5B9',  // 라인 색
							pointBackgroundColor: '#eb5e28', // 꼭지점 동그라미 색상
							pointRadius: 3, // 동그라미 크기
				            fill: true,    // 영역 채우기 여부
				            tension: 0.1  	// 선의 곡률
	                    }]
	                },
	                options: {
	                    responsive: true,
	                    scales: {
	                        x: {
	                            title: {
	                                display: true,
	                                //text: '날짜'
	                            }
	                        },
	                        y: {
	                            title: {
	                                display: true,
	                                //text: '접속자 수'
	                            },
	                            min: 0,  // Y축의 최소값을 0으로 설정
	                            ticks: {
	                                stepSize: 1,  // Y축 값을 정수로 표시 (1씩 증가)
	                                beginAtZero: true,  // Y축이 0부터 시작하도록 설정
	                                callback: function(value) {
	                                    return Number.isInteger(value) ? value : '';  // 정수만 표시
	                                }
	                            }
	                        }
	                    },
	                    plugins: {
	                        tooltip: {
	                            callbacks: {
	                                title: function(tooltipItem) {
	                                    return tooltipItem[0].label;  // 날짜 (mm-dd)
	                                },
	                                label: function(tooltipItem) {
	                                    const dateIndex = tooltipItem.dataIndex;
	                                    const visitAmount = visited_cnt[dateIndex];
	                                    return '방문자 수 ' + visitAmount.toLocaleString() + ' 명';
	                                }
	                            }
	                        }
	                    }
	                }
	            });
	
	            // 새로운 차트를 보이게 하기
	            $("canvas#total_reserved_chart").show();
	        },
	        error: function() {
	            alert("30일간 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
			
	    });// end of $.ajax({})--------------------------------------
	}, 900);
	// ==== 방문자 수 차트 끝  ==== // 

});// end of $(document).ready(function(){})----------------------------


