///*
//* To change this license header, choose License Headers in Project Properties.
//* To change this template file, choose Tools | Templates
//* and open the template in the editor.
//*/
//package bank;
//
//import java.util.ArrayList;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PUT;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.MediaType;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// * REST Web Service
// *
// * @author Mourhaf
// */
//@Path("generic")
//public class GenericResource {
//    
//    @Context
//    private UriInfo context;
//    
//    /**
//     * Creates a new instance of GenericResource
//     */
//    public GenericResource() {
//    }
//    
//    /**
//     * Retrieves representation of an instance of bank.GenericResource
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public String getXml() {
//        
//        //TODO return proper representation object
//        return "<bank navn='merkur'><kunder><kunde navn='Jacob' kredit='1000'/></kunder></bank>";
//        //throw new UnsupportedOperationException();
//    }
//    @Path("tekst")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getTekst() {
//        System.out.println("getTekst() blev kaldt fra "+context);
//        return "Der er en bank, der hedder Merkur, og den har kunden Jacob med 1000 kr";
//    }
//    @Path("jsontest")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJson() {
//        System.out.println("getJson() blev kaldt fra "+context);
//        return "{\"bank\": \"Merkur\",\"kunder\": [{\"navn\": \"Jacob\", \"kredit\": 1000 } ]}";
//    }
//    
//    
//    /**
//     * PUT method for updating or creating an instance of GenericResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putXml(String content) {//**********???
//        
//    }
//    
//    public static class Kunde {
//        public String navn;
//        public double kredit;
//    }
//    
//    @XmlRootElement // Nødvendig for at @Produces(MediaType.APPLICATION_XML) virker!!
//    public static class Bank {
//        public String navn;
//        public ArrayList<Kunde> kunder = new ArrayList<Kunde>();
//    }
//    
//    static Bank bank = new Bank();
//    static {
//        bank.navn = "Merkur";
//        Kunde k = new Kunde();
//        k.navn = "Jacob";
//        k.kredit = 1000;
//        bank.kunder.add(k);
//    }
//    @Path("bankjson")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Bank getBankJson() {
//        System.out.println("getBankJson() blev kaldt fra "+context);
//        return bank;
//    }
//    
//    @Path("bankxml")
//    @GET
//    @Produces(MediaType.APPLICATION_XML) // kræver at Bank-klassen annoteres med @XmlRootElement
//    public Bank getBankXml() {
//        System.out.println("getBankXml() blev kaldt fra "+context);
//        return bank;
//    }
//    
//    @Path("kunde/{navn}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Kunde findKunde(@PathParam("navn") String navn) {
//        System.out.println("findKunde("+navn+") blev kaldt fra "+context);
//        for (Kunde kunde : bank.kunder) {
//            if (kunde.navn.equals(navn)) return kunde;
//        }
//        System.out.println("findKunde("+navn+") intet fundet ");
//        return null;
//    }
//    
//    
//    @Path("opret")
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void opretKunde(Kunde k) {
//        System.out.println("opretKunde("+k+") blev kaldt fra "+context);
//        bank.kunder.add(k);
//    }
//    
//    
//    
//}
