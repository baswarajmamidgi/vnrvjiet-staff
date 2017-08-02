package com.baswarajmamidgi.vnrstaff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by baswarajmamidgi on 21/01/17.
 */

public class Cardadapter extends RecyclerView.Adapter<Cardadapter.MyViewHolder> {
    public Context context;
    public List<Carddetails> carddetailsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.thumbnail);
            textView = (TextView) view.findViewById(R.id.textView);


        }
    }
    public Cardadapter(Context context,List<Carddetails> carddetailsList) {
        this.context=context;
        this.carddetailsList=carddetailsList;

    }
        @Override
        public Cardadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(final Cardadapter.MyViewHolder holder, int position) {
            final Carddetails carddetails=carddetailsList.get(position);
            holder.textView.setText(carddetails.getName());
            holder.imageView.setImageResource(carddetails.getThumbnail());
            holder.textView.setText(carddetails.getName());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String activity=carddetails.getName();
                    Log.i("log cardadapter",activity);
                    switch (activity)
                    {
                        case "Departments":
                        {
                        Log.i("log","departments clicked");
                        PopupMenu popupMenu=new PopupMenu(context,holder.imageView);
                        popupMenu.getMenuInflater().inflate(R.menu.department_menu,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                String branch=item.getTitle().toString();
                                Log.i("log",branch);
                                Intent i=new Intent(context,Faculty.class);
                                i.putExtra("branch",branch.toLowerCase());
                                context.startActivity(i);

                                return true;
                            }
                        });
                        popupMenu.show();
                            break;
                        }
                        case "Library" :
                        {
                            context.startActivity(new Intent(context,Library.class));
                            break;
                        }

                        case "Events & Workshops":
                        {try {
                            Intent i = new Intent(context, Workshopslist.class);
                            context.startActivity(i);
                            break;
                        }catch (Exception e){
                            Log.i("workshop error",e.getLocalizedMessage());
                        }
                        }
                        case "Gallery":
                        {
                            context.startActivity(new Intent(context,Gallery.class));
                            break;
                        }
                        case "Results":
                        {
                            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "Documents":
                        {
                            context.startActivity(new Intent(context,Uploaddocs.class));
                            break;
                        }

                        case "Creative arts":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.arts);
                            bundle.putString("fblink","https://www.facebook.com/creativeartsatvnr/?hc_ref=SEARCH");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo"," Look around you.There is art everywhere.From the symmetrical railway lines to the vivid colors of life.Art manifests itself in a myriad ways.\n \n"+"Art inspires poetry, music, and painting.It is a beautiful expression of the artistic soul.If life inspires the artist within you, visualize the aesthetic, cultural, and devotional sentiments that paint the creative minds.\n \n"+"Our club invites all the variants of art so that we can make a beautiful rainbow when we integrate our individual skillsets");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;

                        }
                        case "Crescendo":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.cres);
                            bundle.putString("fblink","https://www.facebook.com/CrescendoVNRVJIET/");
                            bundle.putString("youtube","https://www.youtube.com/channel/UCWerpdygBm_mJy81dKDaceQ");

                            bundle.putString("clubinfo","The official music club of VNR VJIET, CRESCENDO formed in the year 2002 by Prashanth Narayan and Ramakrishna kalicharan with the prime motto of gathering people passionate about making music and jamming for a few hours at the college . \n \n"+" Earlier, students used to get their instruments back from their homes and even by rental to practice at college. Later, the college management was impressed with the growth and the increasing number of people interested in joining the club and sanctioned few instruments for the club. \n"+"The club was successful in gathering people together from the batches entering into and even the ones leaving after their graduation. The club has always been active participating in the cultural activities running up the year such as SINTILLASHUNZ,[2] VECTOR, CONVERGENCE at VNRVJIET .\n \n"+" The club has been part of the music competitions at SAARANG, one of the largest student-organised cultural and social festival at IIT-MADRAS, for about 14 years by now. CRESCENDO, has all around 25 original musical compositions in Telugu,Hindi and English languages. Crescendo being mostly a light music band has musical influences from varied genres like Carnatic classial, Bollywood music, Progressive rock and artists like A R RAHMAN, ILAIYARAAJA, AGAM, MOTHERJANE, INDIAN OCEAN and many more.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "Dramatrix":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.drama);
                            bundle.putString("fblink","https://www.facebook.com/DramatrixAtVNR/");
                            bundle.putString("youtube","https://www.youtube.com/user/DramatrixAtVNR");
                            bundle.putString("clubinfo"," Dramatix club is a very active club in VNR VJIET with very potential dramatists and their award winning performances. The Drama Club works on speech,stage presentation and theatre production.\n \n"+"The goal is to present plays and various performances like skits,mono acting,anchoring,street dramas,radio plays etc. CHEERS to the dramatists in The Dramatix");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "ED cell":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.edcell);
                            bundle.putString("fblink","https://www.facebook.com/groups/733637433336743/");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo","VNRVJIET ED Cell has come into existence from August,2006. It is nurtured with additional inputs like regular class work by internal faculty, guest lectures, e- Talks by entrepreneurs idea generation workshops and seminars."+
                                            "\nVNRVJIET has obtained permission to undertake EDC Project from 2011-2012 with AICTE funds for a period of 3 years.\n"+
                                    "\n INVITATION TO YOUNG ENTREPRENEURS:\t\n"+
                                    "\n" + "We offer incubation facilities for upcoming young entrepreneurs to incubate their ideas in any proposed area of their interest.\n" +
                                            "\n" +
                                            "\n" +
                                            "We offer consultancy services for industrial units owned by entrepreneurs and also family business units owned by our students. We help them in running their units as profit centers.\n" +
                                            "\n" +
                                            "\n" +
                                            "We offer our services as mentors for your business. We also extend our services in reviving sick units. \n" +
                                            "Contact us at edcell@vnrvjiet.in");

                                i.putExtras(bundle);
                            context.startActivity(i);
                            break;

                        }
                        case "Live wire":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.live);
                            bundle.putString("fblink","https://www.facebook.com/Livewirevnr/?hc_ref=SEARCH");
                            bundle.putString("youtube","https://www.youtube.com/channel/UC3Jy39i6KV2uO-TT9Y3DKTg");
                            bundle.putString("clubinfo","Being established in 2005 ,Livewire is the official All style dance club of VnrVjiet ! Proficient in various dance forms mainly Hip-Hop  !"+
                                    "\n \n We dance for pride ,we dance for screams ,we dance for glory !"+
                                    "\n\n Livewire has competed with crews all over india and won many of them :"+
                                    "\n Winners at IIT HYD for 5times"+
                                    "\n Winners at IIIT HYD for 8times    Winners at NITW"+
                                    "\n Runner ups at bits hyd and this list goes on !"+
                                    "\n\n \"Practice like you never won,"+
                                    "Perform like you never lost is what we belive in !!\" ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }

                        case "NSS":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.nss);
                            bundle.putString("fblink","https://www.facebook.com/nssvnrvjiet/");
                            bundle.putString("youtube","https://www.youtube.com/channel/UCuE9x7eUYfFCG4D_rYXcxRA");

                            bundle.putString("clubinfo","National Service Scheme (NSS) is a public service campaign conducted by the Ministry of Youth Affairs & Sports .Govt. of India has around 3.2 million student volunteers on its roll, who work with primary focus on the development of personality, through community service.\n \n"+" NSS unit VNR VJIET was established in the year 2006. Ever since then, it has organised many events that are focused on the welfare of society like free health camps, blood donation camps, plantation programmes, awareness drives and many other events. \n" +
                                    "\n" + "The NSS team aims to inculcate social welfare in students, and to provide service to society without bias.NSS volunteers work to ensure that everyone who is needy gets help to enhance their standard of living and lead a life of dignity.\n"+" In doing so, volunteers learn from people in villages how to lead a good life despite a scarcity of resources.\n \n"+" With a driving motto, “not me, but you” indicating selfless service, NSS unit was able to give responsible citizens to our society. ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "Scintillate":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.scintilate);
                            bundle.putString("fblink","https://www.facebook.com/scintillateatvnr/");
                            bundle.putString("youtube","https://www.youtube.com/channel/UCaBLoiHBg-o2uFo5EEhlVXA");

                            bundle.putString("clubinfo","  Many things happen in the college. Sports, academics, special events, memories, nature, performances, flash mobs and many such impromptu occurences.\n"+" We the students of VNR have recorded memories off all of these events, thanks to the Photography Club of our college. It is called Scintillate. A flash and an image realisic than what our eyes can see is what they provide us. Get sintillated with the memories given by Scintillate.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "Stentorian":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.stento);
                            bundle.putString("fblink","https://www.facebook.com/stentorianvnrvjiet/");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo"," The Literary Club, established as the Stentorian, aims at fostering a love for books and literature in students, and promoting literary activities in college and the city.\n \n"+"The wide spectrum of literary activities undertaken by the Literary Club includes book discussions, interaction with authors, review writing competitions, literary quizzes, visits to book fairs and libraries, workshops, Group discussion's, Debates, rapid jam's etc.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "Team R & D":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.teamrandd);
                            bundle.putString("fblink","https://www.facebook.com/groups/206314623173556/");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo","Team Research and Development, is a group of enthusiastic students,brought togther by creative intuition and bonded by the strong aspiration to contribute to cutting edge technologies. \n \nAs a team,we make leaders who are ready to take up challenges. Here we learn, share and excel in our dream technologies making us ready to contribute to  the  technological revolution.\n\n A year spent here wisely would definitely leave any student well acquainted with atleast one technical domain and a good knowledge in others.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "Team Robotics":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.robotics);
                            bundle.putString("fblink","https://www.facebook.com/TR.VNRVJIET/");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo","Team Robotics VNR VJIET is an innovative club working towards building useful robots for the society. From racing bots to humanoids , this club does it all.\n \n"+" This logo depicts the three important aspects of robotics : materials, electronics and mechanics.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "VJ Teatro":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.teatro);
                            bundle.putString("fblink","https://www.facebook.com/vjteatro/?fref=ts");
                            bundle.putString("youtube","https://www.youtube.com/user/shootitloveit");
                            bundle.putString("clubinfo","VJ TEATRO is the official short film club of VNR VJIET . One of the best short films clubs in the South India and it is well known for its short films like Devuda, Swachh VNR, The First Meet, A Phone Call, and How i met myself, among others.\n \n"+" In 2015-16 academic year almost 250 students have reported for the auditions. In simpler words it is the pride of VNR VJIET to have a club like that running with the tag line \"SHOOT IT. LOVE IT\" ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "VJSV":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.vjsv);
                            bundle.putString("fblink","https://www.facebook.com/tlcvnrvjiet/");
                            bundle.putString("youtube",null);

                            bundle.putString("clubinfo",null);
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;

                        }
                        case "VNR SF":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.vnrsf);
                            bundle.putString("fblink","https://www.facebook.com/VNRStudentForce/");
                            bundle.putString("youtube",null);
                            bundle.putString("clubinfo"," VNR STUDENT FORCE - fight for the change \n"+
                                    "is  one of the responsive and vibrant social clubs of our college- VNRVJIET.\n Since its inception in the year 2012 it has undergone through a wide range of transformation. It believes in the transformation of an individual which ultimately leads to the transformation of the nation.\n \n VNRSF tries to educate, enlighten and empower the society with it's thoughts and actions. VNRSF works on  4 domains like education, cognizance, environment and charity.\n \n Though it's actions represent a drop of an ocean... it believes that,if the drop were not there, the ocean would be missing something.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "ASME":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.asme);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/VNRASME/?hc_ref=SEARCH&fref=nf");
                            bundle.putString("clubinfo"," In the Institute ASME (American Society of Mechanical Engineers) Student Chapter was started. The formal inauguration of the chapter was done on March 13, 2013.\n There are nearly 100 student members as at present. The student members are from both Mechanical Engineering and Automobile Engineering streams. We have a senior professor as the faculty advisor. \n \nThe chapter is actively engaged in conducting various guest lectures, seminars from eminent persons from various research organizations like DRDO, CMTI, GTRE to name a few, apart from industries like Veljan, Mahendra & Mahendra etc. The student members arranged a quiz programme and also successfully undertaken a project SEGWAY, a two wheeled self balancing vehicle. ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }


                        case "CSI":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.csi);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/csionlinepage/?hc_ref=SEARCH");
                            bundle.putString("clubinfo","Computer Society of India is the largest association of IT professionals in India. Started in the year 1965 with less than 50 like minded professionals as a computer user group.\n \n It has grown to a size of 18000 members in a span of about three decades. The purposes of the society are scientific and educational directed towards the advancement of the theory and practice of computer science, computer engineering and technology, systems science and engineering, information processing and related arts and sciences.\n \n It is a professional body where professionals meet to exchange views and information, to learn and share ideas. The activities of the Chapters/Student branches, include lecture meetings seminars, conferences, training programmes and visits to installations \n"+
                                "\n \n The Computer Science and Engineering department of VNR Vignana Jyothi Institute\n" +
                                            "of Engineering and Technology, Hyderabad. Inaugurated its CSI student branch activities on\n" +
                                            "the 6 th of October, 2004 for the academic year 2004-2005. The Chief Guest of the day was\n" +
                                            "Dr.Shaukat A Mirza. Secretary, Hyderabad chapter.");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }


                        case "IEEE":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.ieee);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/ieeevnrvjiet/");
                            bundle.putString("clubinfo", "IEEE (Institute of Electrical & Electronic Engineers) is the worlds' leading resource for technological innovation and professional network in the fields of electrical,electronic & computing. A professor acts as counsellor for the chapter and other office bearers are nominated by the counsellor.\n Any student belonging to ECE, EEE, EIE, CSE, CSIT, ME and CE disciplines can become a member of this chapter by paying the required fee of Rs.1300/- per year to the treasurer, IEEE international through the local chapter.\n"+
                                            "\n \nThe IEEE student branch of our college was formed on August 27th, 1998.It was inaugurated by the then IEEE HydSec Chair Mr. N.S.S Prasad on October 24th, 1998.\n"+
                                            "\n \nThe members of the chapter are eligible to participate in all the activities of the chapters and also in the student seminars and paper contests organised by the IEEE chapters located anywhere in the country & abroad and help develop their technical skills. Membership of this international professional body carries weightage in the admission to post-graduate programmes in U.S and other foreign universities.\n The members also develop organizing skills by organizing various events. They will also have access to the special infrastructural facilities developed by the chapter in the institute.\n");

                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "IEI":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.iei);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/VNRVJIETIEI/");
                            bundle.putString("clubinfo"," IEI Student Chapter of Electrical and Electronics Engineering, VNR VJIET Institute of Engineering and technology aims to make every student here in VNR VJIET to contribute his own bit to science and technology.\n \n  It was on September 10, 2012 IEI Student Chapter was born with the vision to give students the opportunity to meet and learn from fellow students, as well as faculty members and professional in the field.\n We at VNR VJIET Institute of Engineering and technology believe in building technology for people and people for technology. The Chapter activities offer numerous educational, technical, and professional opportunities through various events");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }

                        case "ISTE":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.iste);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/istevnrvjietofficial/");
                            bundle.putString("clubinfo"," ISTE is the premier professional society in India of engineering teachers, administrators and students. The faculty, students andmanagement of VNRVJIET took interest in establishing ISTE student chapter, VNRVJIET.\n The formation of ISTE Student Chapter was approved in the year 1998. The ISTE Student Chapter, VNR VJIET is the most active student body in the college with its horizons set atnowhere. \nSince its inaugural in 1998 in the college, it has expanded by leaps and bounds, conducting various technical and extracurricular activities. ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        case "ISOI":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.isoi);
                            bundle.putString("youtube",null);

                            bundle.putString("fblink","https://www.facebook.com/IsoiVnr/?hc_ref=SEARCH");
                            bundle.putString("clubinfo"," ISOI (Instrument Society of India) is a premier professional organisation in India for Instrumentation Engineers. Any student belonging to EIE, EEE, ECE, CSE and CSIT disciplines can become a member of this chapter.\n \n"+
                                   "The Student Chapter Of India (ISOI), VNR VJIET, was formally inaugurated on the 2nd September,2003,by Dr. Prahlada, Director, DRDL.\n The soul of this student chapter has always been its impeccable commitment to achieve the objective of broadening the technological base of the students by providing the necessary impetus. This Student Body elected its Executive Members under the able guidance of the Faculty Coordinator,S.Pranavanand. ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }

                        case "TED x":
                        {
                            Intent i = new Intent(context, Viewclubdetails.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("name",activity);
                            bundle.putInt("image",R.drawable.tedx);
                            bundle.putString("youtube","https://www.youtube.com/channel/UCA1D3CbCztYCaZFrUYdo2cA");

                            bundle.putString("fblink","https://www.facebook.com/tedxvnrvjiet/");
                            bundle.putString("clubinfo","In the spirit of ideas worth spreading, TED has created a program called TEDx. TEDx is a program of local, self-organized events that bring people together to share a TED-like experience. \n \nOur event is called TEDxVNRVJIET, where x = independently organized TED event. At our TEDxVNRVJIET event, TED Talks (videos) and live speakers will combine to spark deep discussion and connection in a small group.\n"+
                                    "TEDxVNRVJIET is a local, independently organized TED event in Hyderabad, India. that strives to re-create the unique experience found at TED. \n\nAt its core, the fundamental goal is to promulgate \"ideas worth spreading\" ");
                            i.putExtras(bundle);
                            context.startActivity(i);
                            break;
                        }
                        default:
                        {
                            Toast.makeText(context, "No activity found", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return carddetailsList.size();
        }
    }

