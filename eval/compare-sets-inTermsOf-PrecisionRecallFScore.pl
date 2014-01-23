#!/usr/bin/perl -CDS
#
# Comparison of set in terms of Precision, Recall and F mesure 
# By Nicolas HERNANDEZ 
# 

#-- PACKAGES
#-----------------------------------------------------------------------------#
use strict;


#-- PARAMETERS
#-----------------------------------------------------------------------------#
my $TRUE = 1;
my $FALSE = 0;

my $refFile;
my %listFileAComparer = (); 


while (my $opt = shift) {
    $_ = $opt;
  SWITCH: {
      if (/^\-r$/) {
	  $refFile = shift;
	  last SWITCH;
      }
      if (/^\-c$/) {
	  my $f = shift;
	  $listFileAComparer{$f}++;
	  last SWITCH;
      }
      &error();
      
  }
}

if ($refFile eq '') {      &error();}
if ((keys(%listFileAComparer)) <= 0)  {      &error();}

sub error() {
      print STDERR "Description: - compare les mesures de précision, rappel et F mesure par rapport à une liste référente\n";
      print STDERR "
précision n.f.
     Rapport du nombre de documents pertinents trouvés au nombre total de documents sélectionnés. En anglais precision.
       
rappel n.m.
     Rapport du nombre de documents pertinents trouvés au nombre total de documents pertinents. En anglais recall.     

  Ainsi, si l'on note S l'ensemble des documents qu'un système automatique considère comme ayant une propriété recherchée, V l'ensemble des documents
qui possèdent effectivement cette propriété, P et R respectivement la précision et le rappel du système : 

                                            | S ^ V |                 | S ^ V | 
                                       P = -----------           R = -----------
                                              | S |                     | V | 

La F-mesure [van Rijsbergen, 1979] permet de combiner en une seule valeur les mesures de précision et de rappel de manière. Elle est exprimée par la
formule :  
	F =	2 (b+1) * P * R
		-----------
		  b* P + R     
Le paramètre 'b' permet de régler les influences respectives de la précision P et du rappel R. Il est très souvent fixé à 1. 

We will also use the F-measure (Van Rijsbergen, 1979) which combines recall and precision in a single efficiency measure (it is the
                    harmonic mean of precision and recall) 
\n";
      print STDERR "Usage: perl $0 -r referenceSetFile [-c candidateSetFile]+ \n";
      die "perl $0  \n";
}


#-----------------------------------------------------------------------------#
#-- PROCESSING

print STDERR "Info: loading the gold set>$refFile<\n";
open (REFIN, "$refFile");
my %listRef = ();
while (<REFIN>) {
    chomp;
    $listRef{$_}++   
}
close REFIN;

my $nbItemListRef = keys (%listRef);
my $v = $nbItemListRef ;

#my $tmp_dir = $refFile;
$refFile =~ /(\/[^\/]+)$/;
my $tmp_file = $1;
$tmp_file =~ /^([^\.]+)\./;
my $headRefFile =  $1;
$headRefFile =~ s/\///g;


my $P;
my $R;
my $F;
my $ref = $headRefFile;
my $cur ;
my $i;
my $s;

format Something =
@||||||||||||||||||||  &  @||||||||||||||||||||||||||||||| &  @>>>>>  &  @>>>>>  &  @>>>>> &  @>>>>>  &  @>>>>>  &  @>>>>>
$headRefFile, $cur, $v,     $s,    $i, $P,     $R,    $F
.
#### Attention ce . doit être coller au bord gauche !!!!!!!!!!!!!!!!!!!!!!!!!


    
#  $str = "widget";
#  $num = 1.23;


#
print STDERR "Info: processing each candidate set\n";

print STDERR "headRefFile\tcur\tv\ts\ti\tP\tR\tF\n";

foreach my $lf (keys(%listFileAComparer)) {
    my %listCurr = ();
    
    #
    $lf =~ /(\/[^\/]+)$/;
    my $tmp_file = $1;
    $tmp_file =~ /^([^\.]+)\./;
    my $headCurrFile =  $1;
    $headCurrFile =~ s/\///g;
    $cur =  $headCurrFile;
    #
    open (CURRIN, "$lf");

    while (<CURRIN>) {
	chomp;
	$listCurr{$_}++ ;
    }
    close CURRIN;

    #
    my $nbItemListCurr = keys (%listCurr);
    $s = $nbItemListCurr;

    # commun
    my $nbInterList = 0;
    foreach my $e ( keys (%listRef)) {
	if (exists($listCurr{$e})) {$nbInterList++}
    }
    $i = $nbInterList;

    #print "Debug:liste>$cur<\tvalide>$v<\tintersection>$i<\tsélectionné>$s<\n";

    # Précision
    if ($i == 0) {$P = 0} 
    else {
	$P = $i / $s;}

    # Rappel
    $R = $i / $v;

    # F-mesure
    if (($P == 0) || ($R == 0)) { $F = 0} 
    else 
    {$F = (2 * $P * $R) / ($R + $P);}

    #printf "$headRefFile\#$headCurrFile\tPrecision>%5.4f<\tRecall>%5.4f<\tF-score>%5.4f<\n", $P, $R, $F;
    $~ = 'Something';
    write;
} 





