$(document).ready(function() {
$('#fullpage').fullpage({
sectionsColor: ['#1bbc9b', '#4BBFC3', '#FFE066', '#DCDCDC', '#D69999'],
anchors: ['home', 'education', 'skills', 'projects', 'contact'],
menu: '#menu',
easing: 'easeInQuart',
scrollingSpeed:1800,
resize:true,
continuousVertical: true,
afterLoad: function( anchorLink, index){
	if(anchorLink == 'skills')
	{
		var opts = {
			lines: 12, // The number of lines to draw
			angle: 0.35, // The length of each line
			lineWidth: 0.06, // The line thickness
			pointer: {
				length: 0.9, // The radius of the inner circle
				strokeWidth: 0.035, // The rotation offset
				color: '#000000'
			},
			limitMax: 'false',
			colorStart: '#FFFF33',   // Colors
			colorStop: '#3399FF',    // just experiment with them
			strokeColor: '#E0E0E0',   // to see which ones work best for you
			generateGradient: true
			};
		var target = document.getElementById('cpp'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(80);
		var target = document.getElementById('java'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(90);
		var target = document.getElementById('python'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(85);
		var target = document.getElementById('mySql'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(90);
		var target = document.getElementById('html5'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(95);
		var target = document.getElementById('css'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(90);
		var target = document.getElementById('php'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(90);
		var target = document.getElementById('js'); // your canvas element
		var donut = new Donut(target).setOptions(opts); // create sexy gauge!
		donut.maxValue = 100; // set max gauge value
		donut.animationSpeed=40;
		donut.set(85);
		}
	}
});
});		
// JavaScript Document