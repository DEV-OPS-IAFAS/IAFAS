function SombreaFondo(obj,tip){
	var a = document.getElementById(obj).style;
	if(tip=='F'){
	    a.margin="2px";
	    a.padding="3px";
	    a.font="11px tahoma";
		a.background="#FCFBBE";
	    a.border="1px solid #666666";		
	}else{
	    a.margin="2px";
	    a.padding="3px";
	    a.font="11px tahoma";
	    a.background="#FFF";
	    a.border="1px solid #d9d9d9";		
	}
}


function pulsar(obj){
	var boton = document.getElementById(obj).style;
	boton.position = "relative";
	boton.border_bottom_style="solid";
	boton.border.left="2px";
	boton.left = "1px";
	boton.top = "1px";		
}

function Nopulsar(obj){
	var boton = document.getElementById(obj).style;
	boton.position = "relative";
	boton.left = "0px";
	boton.top = "0px";		
}

function ntab(key, obj){
	if(key==13) { 
		document.getElementById(obj).focus();
	}
	if(key == 34) return (key == 0);
	if(key == 37) return (key == 0);
	
}

function nsolNum(key, obj){
	if(key==13) { 
		document.getElementById(obj).focus();
	}
	else return (key <= 13 || (key >= 48 && key <= 57));				
}

function completaCeros(obj,tam) {
	var txt = document.getElementById(obj).value;
	var tamTxt = txt.length;
	var i;

	for(i=tam; i>tamTxt; i--) txt = '0' + txt;
	
	document.getElementById(obj).value = txt;
}

function ntabNum(evt, obj_val, obj){
    var nav4 = window.Event ? true : false;
    var key = nav4 ? evt.which : evt.keyCode;
    var punto="";
    var sw=0;
    if(key==13) {
        eval('getElementById(' + obj + ').focus()');
    }
    punto = eval('getElementById(' + obj_val + ').value');
    for(var i=1;i<=punto.length;i++){
        tmp = punto.substring(i-1,i)
        if(tmp=="." && key == 46) {
            sw=1 ;
            break;
        }
    }
    if(sw ==1) return (key == 0);
    else return (key == 46 || key <= 13 || (key >= 48 && key <= 57));
}
















