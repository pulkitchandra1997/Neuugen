package peoplecitygroup.neuugen.common_req_files;

import java.util.ArrayList;

public class UrlNeuugen {
    private static final String URL="http://neuugen.com/app/neuugen/backendcode/";
    public static final String send_otp=URL+"sendOtp.php";
    public static final String send_custom_msg=URL+"sendCustomMsg.php";
    public static final String get_profile_login=URL+"getProfileLogin.php";
    public static final String set_basic_userdetails=URL+"setBasicUserDetails.php";
    public static final String fill_RentHouses=URL+"fillRentHouses.php";
    public static final String uploadPic=URL+"uploadPics.php";
    public static final String profilenewpic=URL+"profilenewpic.php";
    public static final String chechActiveService=URL+"checkActiveService.php";
    public static final String sendSuccessMails=URL+"sendSuccessMails.php";
    public static final String fill_RentOffice=URL+"fillRentOffices.php";
    public static final String checkCityService=URL+"checkCityService.php";
    public static final String fill_SellHouses=URL+"fillSellHouses.php";
    public static final String fill_SellPlots=URL+"fillSellPlots.php";
    public static final String checkappversion=URL+"versionCheck.php";
    public static final String requestservice_Salon=URL+"requestServiceSalon.php";
    public static final String requestservice_Learning=URL+"requestServiceLearning.php";
    public static String requestservice_Homerenovation=URL+"requestServiceHomeRenovation.php";
    public static String requestservice_EventsArrangementsForm=URL+"requestServiceEventsArrangements.php";
    public static String requestservice_EventPhotographyForm=URL+"requestServiceEventsPhotography.php";
    public static String requestservice_PreWedShootForm=URL+"requestServicePreWedShoot.php";
    public static String requestservice_ApplianceRepair=URL+"requestServiceApplianceRepair.php";
    public static String requestservice_WeddingShootForm=URL+"requestServiceWeddingShoot.php";
    public static String searchAd=URL+"searchAd.php";
    public static String getOwnAds=URL+"getOwnAds.php";
    public static String showInterest=URL+"showInterest.php";
    public static String checkStatus=URL+"checkStatus.php";
    public static String viewBookings=URL+"viewBookings.php";
    public static String cancelBooking=URL+"cancelBooking.php";
    public static String viewInterestedAds=URL+"viewInterestedAds.php";
    public static String cancelInterestedAd=URL+"cancelInterestedAd.php";
    public static String deleteAd=URL+"deleteAd.php";
    public static String getSpareparts=URL+"getSpareParts.php";

    public static final String csemail="support@neuugen.com";
    public static final String csphone="+919335927026";

    public static final String aboutus=URL+"aboutneuugen.php";
    public static final String termsofuse="http://neuugen.com/tnc.php";
    public static final String privacy="http://neuugen.com/pp.php";
    public static final String helpSupportActivity=URL+"neuugenfaq.php";
    public static final String protectionprogram=URL+"protection.php";


    public static final String fill_EditProfile=URL+"fillEditProfile.php";
    public static final String fill_Customercare=URL+"fillCustomercare.php";



    public static final String salonServiceId="1";
    public static final String mensalonServiceId="2";
    public static final String womensalonServiceId="3";
    public static final String menHaircutId="4";
    public static final String menHaircutBeardId="5";
    public static final String menPartymakeupId="6";
    public static final String womenhaircutId="7";
    public static final String womenPartymakeupId="8";
    public static final String womenWeddingmakeupId="9";
    public static final String appRepairInstallId="10";
    public static final String repairingId="11";
    public static final String installationId="12";
    public static final String homeRenovationId="13";
    public static final String plumbingId="14";
    public static final String electricianId="15";
    public static final String carpentryId="16";
    public static final String learningServiceId="17";
    public static final String actingClassId="18";
    public static final String dancingClassId="20";
    public static final String groomingClassId="19";
    public static final String eventsServiceId="21";
    public static final String photoVideoServiceId="22";
    public static final String eventArrangementId="23";
    public static final String preWedShootId="24";
    public static final String weddingShootId="25";
    public static final String eventPhotogaphyId="26";
    public static final String normalWedShootId="27";
    public static final String standardWedShootId="28";
    public static final String premiumWedShootId="29";
    public static final String danceperformid="30";
    public static final String anchorshostid="31";
    public static final String singersid="32";
    public static final String bandsmusiciansid="33";
    public static final String propertiesserviceid="34";

    private static final String GoogleApiKey_1="UVVsNllWTjVRbUpXZU";
    private static final String GoogleApiKey_2="RsdlFqbENObU0wTVcx";
    private static final String GoogleApiKey_3="aFpDMWFXbVJEZGs0NW";
    private static final String GoogleApiKey_4="VuZEtSRGg0WWs4dwo=";


    public static String returnKey_1(){
        return GoogleApiKey_1;
    }
    public static String returnKey_2(){
        return GoogleApiKey_2;
    }
    public static String returnKey_3(){
        return GoogleApiKey_3;
    }
    public static String returnKey_4(){
        return GoogleApiKey_4;
    }



    public static final ArrayList<String> createId() {
        ArrayList<String> idList = new ArrayList<String>();
        for (int i = 1; i < 35; i++)
            idList.add(String.valueOf(i));
        return idList;
    }
    public static final String[] serviceName={"","Grooming","Men's Salon","Women's Salon","Men's Haircut","Men's Haircut and Beard","Men's Party Grooming","Women's Haircut and Styling","Women's Party Makeup","Women's Wedding Makeup","Appliance Repair","Repairing","Installation","Home Renovation","Plumbing","Electrician","Carpentry","Learning","Acting Classes","Makeup and Grooming Classes","Dancing Classes","Events","Photography and Videography","Events Arrangements","Pre Wedding Shoot","Wedding Shoot","Event Photography","Normal Package","Standard Package","Premium Package","Dance Performers","Anchors or Hosts","Singers","Bands and Musicians","Properties"};
}
