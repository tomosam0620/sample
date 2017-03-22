$(function() {
	$('.datepicker').datepicker({
		language: 'ja',
		format : 'yyyy-mm-dd',
		autoclose: true
	});
});

$(function(){
	$('.timepicker').timepicker({
		minuteStep: 1,
	    template: 'false',
		showInputs: false,
		showMeridian: false
	});
});

$(function(){
	$('.selectMonth').change(function(){
		window.location.href = $(this).val();
	});
});

