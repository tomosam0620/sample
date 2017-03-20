$(function() {
	$('.datepicker').datepicker({
		language: 'ja',
		format : 'yyyy-mm-dd',
		autoclose: true
	});
});

//$(function() {
//	$('.timepicker').timepicker({
////	    format: 'HH:mm',
//	    template: 'modal',
////	    minuteStep: 1,
////	    stepping: 1,
//	    showMeridian: false,
//		autoclose: true,
//		
//		minuteStep: 1,
//		showInputs: false,
////        disableFocus: true
//    });
//});


$(function(){
	$('.timepicker').timepicker({
		minuteStep: 1,
	    template: 'false',
		showInputs: false,
		showMeridian: false
	});
});
