function submitRequestForm(ttlNopxn,event)
      {
       		var radioValue="";
			var groundRadioValue=true;
			var cancelRadioID;  
    		
   			if(ttlNopxn > 0)
   			{
    		
    			for(var i=ttlNopxn;i>=1;i--)
    			{
    		
    				
			var cancelStatus=$("#cancelStatus_"+i).val();
    		if(cancelStatus == 'For Cancellation'){
    				
    				var strval="isOnGovtInt_"+i;
    				
    				cancelRadioID=document.getElementsByName(strval);
    				
    				if(cancelRadioID!=null)
    				{
						
    					if(cancelRadioID.length>1)
    					{
    		
				      		if(radioValue=="")
				    		{
					    		if(cancelRadioID[0].checked==true)
					    		radioValue=cancelRadioID[0].value;
					    		else if(cancelRadioID[1].checked==true)
					    		radioValue=cancelRadioID[1].value;
				    		}
    						else
    						{
    							if(cancelRadioID[0].checked==true)
    							{
    								if(radioValue!=cancelRadioID[0].value)
    								{
							    		groundRadioValue=false;
							    		radioValue="";
							    		break;
    								}
    							}
    							else if(cancelRadioID[1].checked==true)
    							{
    								if(radioValue!=cancelRadioID[1].value)
						    		{
							    		groundRadioValue=false;
							    		radioValue="";
							    		break;
						    		}
    							}
    						} //End of else
 	  					} 
    				}
    				}// end of if for cuurentCancel
    				
    			} //End of loop
    		}  //End of ifif(ttlNopxn > 0)
   
    		if(!groundRadioValue)
    		{
	    		alert("Cancellation On Official Ground Should Be Same For All Selected Passenger");
	    		return false;
    		}
    	
		  $("#event").value=event;
		   //document.getElementById("event").value="approve";
		  
	       $("#bookingDetailsApp").submit();	
		}