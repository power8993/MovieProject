
$(document).ready(function(){
	
	
	/*
	// ==== 오늘의 영화 예매 현황(파이차트) 시작 ==== // 
	$.ajax({
		url: 'adminChart.mp',
		type: 'get',
		data: {"data":'pie'},
		async: true,
		
		success: function(json) {
		console.log(json);
		
		
			// 라벨
			const movie_title = [];
			const reserved_cnt = [];
			
			json.forEach(item => {
				movie_title.push(item.movie_title);
				reserved_cnt.push(item.reserved_cnt);
			});
			
			
			// 차트를 넣을 위치
			const today_reserved_chart = $("canvas#today_reserved_chart")[0].getContext('2d');
			const my_pie_chart = new Chart(today_reserved_chart, {
			    type: 'pie',
			    data: {
			        labels: movie_title,
			        datasets: [{
			            label: '예매건수',
			            data: reserved_cnt,
			            backgroundColor: ['#98DDCA', '#8B2635', '#0066CC', '#16A085', '#FFC300', '#2C3E50']
			        }]
			    }
			});
			
		},
		error: function() {
            alert("오늘의 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
        }
	});// end of $.ajax({})--------------------------------------
	// ==== 오늘의 영화 예매 현황(파이차트) 끝  ==== // 
	
	
	// ==== 버튼마다 보여주는 차트를 다르게끔 처리해주는 이벤트 시작 ==== //
	$("button#day_btn").click((e) => {
		$.ajax({
			url: 'adminChart.mp',
			type: 'get',
			data: {"data":'line',
				   "range":'day'
			},
			async: true,
			
			success: function(json) {
			console.log(json);
			
			
				// 라벨
				const day_pay_date = [];
				const day_pay_sum = [];
				const day_reserved_cnt = [];
				
				json.forEach(item => {
					day_pay_date.push(item.day_pay_date);
					day_pay_sum.push(item.day_pay_sum);
					day_reserved_cnt.push(item.day_reserved_cnt);
				});
				
				// 차트를 넣을 위치
				const today_reserved_chart = $("canvas#total_reserved_chart")[0].getContext('2d');
				const my_pie_chart = new Chart(today_reserved_chart, {
				    type: 'line',

					data: {
						labels: day_pay_date,
						datasets: [{
							label: '예매건수',
							data: day_reserved_cnt,
							borderColor: '#eb5e28',  // 라인 색
				            fill: false,     		 // 영역 채우기 여부
				            tension: 0.1  			 // 선의 곡률
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
										return '결제 금액: ' + payAmount + ' 원';    // 결제 금액만 툴팁에 표시
									}
								}
							}
						}
					}
				});// end of new Chart(reserved_chart, {})--------------------------------
			},
			error: function() {
	            	alert("오늘의 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        	}
			});// end of $.ajax({})--------------------------------------
	});// end of $("button#day_btn").click((e) => {})-------------------------
	
	
	
	// ==== 버튼마다 보여주는 차트를 다르게끔 처리해주는 이벤트 시작  ==== //
	$("button#day_btn").click((e) => {
	    // "일별" 차트 요청
	    $.ajax({
	        url: 'adminChart.mp',
	        type: 'get',
	        data: {
	            "data": 'line',
	            "range": 'day'
	        },
	        async: true,
	        success: function(json) {
	            console.log(json);

	            // 데이터를 받아와서 차트를 업데이트
	            const day_pay_date = [];
	            const day_pay_sum = [];
	            const day_reserved_cnt = [];

	            json.forEach(item => {
	                day_pay_date.push(item.day_pay_date);
	                day_pay_sum.push(item.day_pay_sum);
	                day_reserved_cnt.push(item.day_reserved_cnt);
	            });

	            // 기존 차트를 숨기고 새로운 차트를 그리기 전에 기존 차트 제거
	            $("canvas#total_reserved_chart").hide();  // 기존 차트 숨기기

	            // 기존 차트를 제거
	            if (window.totalReservedChart) {
	                destroyChart(window.totalReservedChart);  // 차트 객체를 삭제
	            }

	            // canvas를 초기화
	            const today_reserved_chart = $("canvas#total_reserved_chart")[0].getContext('2d');
	            
	            // canvas 초기화
	            $("canvas#total_reserved_chart").remove();  // 기존 canvas를 완전히 제거
	            $("div#canvas-container").append('<canvas id="total_reserved_chart"></canvas>');  // 새로운 canvas를 추가
	            
	            // 새로운 차트를 그리기
	            const new_today_reserved_chart = $("canvas#total_reserved_chart")[0].getContext('2d');
	            window.totalReservedChart = new Chart(new_today_reserved_chart, {  // 차트 객체를 전역 변수에 저장
	                type: 'line',
	                data: {
	                    labels: day_pay_date,
	                    datasets: [{
	                        label: '예매건수',
	                        data: day_reserved_cnt,
	                        borderColor: '#eb5e28',  // 라인 색
	                        fill: false,             // 영역 채우기 여부
	                        tension: 0.1            // 선의 곡률
	                    }]
	                },
	                options: {
	                    responsive: true,
	                    scales: {
	                        x: {
	                            title: {
	                                display: true,
	                                text: '날짜'
	                            }
	                        },
	                        y: {
	                            title: {
	                                display: true,
	                                text: '예매 건수'
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
	                                    const payAmount = day_pay_sum[dateIndex];
	                                    return '결제 금액: ' + payAmount + ' 원';
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
	            alert("오늘의 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
	    });
	});// end of $("button#day_btn").click((e) => {})

	
	/////////////////////////////////////////////////////////////////////////////////
	
	$("button#month_btn").click((e) => {
	    // "일별" 차트 요청
	    $.ajax({
	        url: 'adminChart.mp',
	        type: 'get',
	        data: {
	            "data": 'line',
	            "range": 'month'
	        },
	        async: true,
	        success: function(json) {
	            console.log(json);
				
				if (Chart.getChart(lineChart)) {
				    Chart.getChart(lineChart)?.destroy();
				}
	            
	            // 데이터를 받아와서 차트를 업데이트
	            const month_pay_date = [];
	            const month_pay_sum = [];
	            const month_reserved_cnt = [];
	            
	            json.forEach(item => {
	                month_pay_date.push(item.month_pay_date);
	                month_pay_sum.push(item.month_pay_sum);
	                month_reserved_cnt.push(item.month_reserved_cnt);
	            });

	            // 이전 차트를 숨기고 새로운 차트를 그리기
	            $("canvas#total_reserved_chart").hide();  // 기존 차트 숨기기

	            // 새로운 차트 그리기
	            const month_reserved_chart = $("canvas#total_reserved_chart")[0].getContext('2d');
	            new Chart(month_reserved_chart, {
	                type: 'line',
	                data: {
	                    labels: month_pay_date,
	                    datasets: [{
	                        label: '예매건수',
	                        data: month_reserved_cnt,
	                        borderColor: '#eb5e28',  // 라인 색
	                        fill: false,             // 영역 채우기 여부
	                        tension: 0.1            // 선의 곡률
	                    }]
	                },
	                options: {
	                    responsive: true,
	                    scales: {
	                        x: {
	                            title: {
	                                display: true,
	                                text: '날짜'
	                            }
	                        },
	                        y: {
	                            title: {
	                                display: true,
	                                text: '예매 건수'
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
	                                    return '결제 금액: ' + payAmount + ' 원';
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
	            alert("한 달간 예매 현황 차트를 불러오는데 실패했습니다. 다시 시도해주세요.");
	        }
	    });
	});// end of $("button#day_btn").click((e) => {})-------------------------
	
	// ==== 버튼마다 보여주는 차트를 다르게끔 처리해주는 이벤트 끝  ==== //
	
	
	
	
	
	// ==== 전체 영화 예매 추이(라인차트) 시작 ==== // 
	

	// ==== 전체 영화 예매 추이(라인차트) 끝  ==== // 
*/
});// end of $(document).ready(function(){})----------------------------

// 차트를 제거하는 함수
function destroyChart(chart) {
    if (chart) {
        chart.destroy();  // 기존 차트 제거
    }
}
