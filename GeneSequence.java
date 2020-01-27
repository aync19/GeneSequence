/**
 * GeneSequence - Finding genes in DNA strands
 * @author Ayan Chaudhry
 *
 */

public class GeneSequence {
    /**
        @param dnaStr a DNA string
        @param startIndex string index of ATG
        @param stopCodon a stop codon
        @return index of stop codon or -1 if one does not exist
        findStopCodon that has three parameters, a String parameter named dna, an integer parameter named startIndex
        that represents where the first occurrence of ATG occurs in dna, and a String parameter named stopCodon.
        This method returns the index of the first occurrence of stopCodon that appears past startIndex and is a
        multiple of 3 away from startIndex.
     */
    public static int findStopCodon(String dnaStr, int startIndex, String stopCodon)
    {
        int currIndex = dnaStr.indexOf(stopCodon, startIndex+3);
        while (currIndex!=-1){
            int diff=currIndex-startIndex;
            if(diff%3==0)
            {
                return currIndex;
            }
            else{
                currIndex=dnaStr.indexOf(stopCodon,currIndex+1);
            }
        }
        return -1;
    }

    /**
         findGene that has one String parameter dna, representing a string of DNA. In this method you should do the following:
         Find the index of the first occurrence of the start codon “ATG”. If there is no “ATG”, return the empty string.
         Find the index of the first occurrence of the stop codon “TAA” after the first occurrence of “ATG” that is a
         multiple of three away from the “ATG”. Hint: call findStopCodon.
         Find the index of the first occurrence of the stop codon “TAG” after the first occurrence of “ATG” that is a
         multiple of three away from the “ATG”. Find the index of the first occurrence of the stop codon “TGA” after
         the first occurrence of “ATG” that is a multiple of three away from the “ATG”.
         Return the gene formed from the “ATG” and the closest stop codon that is a multiple of three away.
         If there is no valid stop codon and therefore no gene, return the empty string.
         @param dna dna
         @param where position in the dna string to start from
         @return the gene or an empty string if the gene does not exist
     */
    public static String findGene(String dna, int where)
    {
        int startIndex=dna.indexOf("ATG", where);

        if(startIndex==-1){
            return "";
        }
        int taaIndex=findStopCodon(dna,startIndex, "TAA");

        int tagIndex=findStopCodon(dna,startIndex, "TAG");

        int tgaIndex=findStopCodon(dna,startIndex, "TGA");

        int minIndex=0;
        if(taaIndex==-1||(tagIndex!=-1&&tagIndex<taaIndex)){
            minIndex=tagIndex;
        }
        else{
            minIndex=taaIndex;
        }
        if(minIndex==-1||(tgaIndex!=-1&&tgaIndex<minIndex)){
            minIndex=tagIndex;
        }

        if(minIndex==-1)return "";
        return dna.substring(startIndex, minIndex+3);
    }

    /**
     * Prints all genes in the dna strand
     * @param dna dna strand
     */
    public static void printAllGenes(String dna){
        int startIndex=0;
        while(true){
            String currentGene=findGene(dna, startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            System.out.println(currentGene);
            startIndex=dna.indexOf(currentGene, startIndex)+currentGene.length();
        }
    }

    /**
     * countGenes that has a String parameter named dna representing a string of DNA. This method returns the number of
     * genes found in dna. For example the call countGenes(“ATGTAAGATGCCCTAGT”) returns 2, finding the gene ATGTAA first
     * and then the gene ATGCCCTAG. This is very similar to finding all genes and printing them, except that instead of
     * printing all the genes you will count them.
     * @param dna dna string
     * @return number of genes in a dna strand
     */
    public static int countGenes(String dna){
        int startIndex=0;
        int counter=0;
        while(true) {
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()) {
                return counter;
            }
            counter += 1;
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
        }
    }
    public static void testFindStopCodon(){
        System.out.println("testFindStopCodon start");
        String dna="ATGTGTGTGTAATTTTTTTTTTAATT";
        int dex=findStopCodon(dna, 0, "TAA");
        if(dex!=9)System.out.println("error - TAA is at index 9");
        dex=findStopCodon(dna,9,"TAA");
        if(dex!=21)System.out.println("error - TAA is at index 21");
        dex=findStopCodon(dna,1,"TAA");
        if(dex!=-1)System.out.println("error - not multiple of 3's away");
        dex=findStopCodon(dna,0,"TAG");
        if(dex!=-1)System.out.println("error - TAG should not exist");
        System.out.println("testFindStopCodon done");

    }
    public static void testFindGene(){
        System.out.println("testFindGene start");
        String dna= "ATGTAATAGTGA";
        System.out.println("dna is: "+dna);
        String gene=findGene(dna, 0);
        System.out.println("gene is: "+gene);
        if (!gene.equals("ATGTAA")) {
            System.out.println("error - gene extracted should be ATGTAA");
        }

        dna= "ATGTAGTAATGA";
        System.out.println("dna is: "+dna);
        gene=findGene(dna, 0);
        System.out.println("gene is: "+gene);
        if (!gene.equals("ATGTAG")) {
            System.out.println("error - gene extracted should be ATGTAG");
        }

        dna= "ATGTGATAGTGA";
        System.out.println("dna is: "+dna);
        gene=findGene(dna, 0);
        System.out.println("gene is: "+gene);
        if (!gene.equals("ATGTGATAG")) {
            System.out.println("error - gene extracted should be ATGTGATAG");
        }

        dna= "ATGcTGAcTAGcTGT";
        System.out.println("dna is: "+dna);
        gene=findGene(dna, 0);
        System.out.println("gene is: "+gene);
        if (!gene.isEmpty()) {
            System.out.println("error - no gene since the stop codon position is not in multiples of 3");
        }

        dna= "ATGbTGTTGTTGTTGTTGTTGTTGATATTGT";
        System.out.println("dna is: "+dna);
        gene=findGene(dna, 0);
        System.out.println("gene is: "+gene);
        if (!gene.isEmpty()) {
            System.out.println("error - no gene since the stop codon position is not in multiples of 3");
        }

        dna = "ATGATCTAATTTATGCTGCAACGGTAAAGA";
        printAllGenes(dna);
        int count = countGenes(dna);
        if (count!=2) {
            System.out.println("error - there are 2 genes in " + dna);
        }
        System.out.println("testFindGene stop");

    }
    public static void main (String[] args) {
        testFindStopCodon();
        testFindGene();
    }
}

