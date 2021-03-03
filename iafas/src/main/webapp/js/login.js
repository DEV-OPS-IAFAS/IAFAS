/**
 * Creado por Elvis Severino
 */
var $loginMsg = $('.loginMsg'),
  $login = $('.login'),
  $signupMsg = $('.signupMsg'),
  $signup = $('.signup'),
  $frontbox = $('.frontbox');

$(function() {
$('#switch1').on('click', function() {
  $('.loginMsg').toggleClass("visibility");
  $('.frontbox').addClass("moving");
  $('.signupMsg').toggleClass("visibility");
  $('.signup').toggleClass('hide');
  $('.login').toggleClass('hide');
	});
});

$(function() {
$('#switch2').on('click', function() {
  $('.loginMsg').toggleClass("visibility");
  $('.frontbox').removeClass("moving");
  $('.signupMsg').toggleClass("visibility");
  $('.signup').toggleClass('hide');
  $('.login').toggleClass('hide');
	});
});

/*
setTimeout(function(){
  $('#switch1').click()
},1000)

setTimeout(function(){
  $('#switch2').click()
},3000)
*/

