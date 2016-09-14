package py.ande.sigedif;

/**
 * Created by asu05894 on 7/9/2016.
 */
public class ConvertUTMtoLatLong {
    Integer X,Y;
    double e=0.08199189;
    Integer a=6378388;
    double e2=0.0067227;
    double eZ=e2/(1-e2);
    double h1=0.00168634;
    double h3=((X-10000000)/0.9996);
    double h2=(h3/(a*(1-e2/4-3*(Math.pow(e,4/64))-5*Math.pow(e,6/256))));
    double h4=(h2+((3*(h1/2)-(27*Math.pow(h1,3/32)))*(Math.sin(2*h2))+((21*Math.pow(h1,2/16)-(55*Math.pow(h1,4/32)))*Math.sin(4*h2))+((151*Math.pow(h1,3/96))*Math.sin(6*h2))+((1097*Math.pow(h1,4/512))*Math.sin(8*h2))));
    double h5=eZ*(Math.cos(Math.pow(h4,2)));
    double h6=Math.pow(Math.tan(h4),2);
    double h7=a/(1-e2*(Math.pow(Math.pow(Math.sin(h4),2),0.5)));









}
