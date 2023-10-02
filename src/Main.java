import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    // F I O Course Group SessionNumber {Subject Mark}

    public static class SetoffBook {
        public static class student {
            String firstName;
            String secondName;
            String fatherName;
            int course;
            int group;

            student(String line) {
                String DividedLine[] = new String[7];

                DividedLine = line.split(" ");
                //debug
                firstName = DividedLine[0];
                secondName = DividedLine[1];
                fatherName = DividedLine[2];
                course = Integer.parseInt(DividedLine[3]);
                group = Integer.parseInt(DividedLine[4]);
            }

            //
        }

        public static class session {

            public static class SubjectAndMark {

                String Subject;
                int Mark;

                public SubjectAndMark(String subject, int mark) {
                    Subject = subject;
                    Mark = mark;
                }

                public void setSubject(String subject) {
                    Subject = subject;
                }

                public void setMark(int mark) {
                    Mark = mark;
                }
            }
            int sessionNumber;

            List <SubjectAndMark> SaM= new ArrayList<>();


            public session(String s) {

                String dividedLine[] = new String[8];

                dividedLine = s.split(" ");

                sessionNumber = Integer.parseInt(dividedLine[1]);
                for (int i=2;i<dividedLine.length;i+=2) {
                    SubjectAndMark sm = new SubjectAndMark(dividedLine[i],Integer.parseInt(dividedLine[i+1]));
                    SaM.add(sm);
                }

            }
        }
        student stud;

        List<session> sessions = new ArrayList<>();


        public SetoffBook(String s) {

            int index =0;
            index = s.indexOf("SessionNumber");
            String forStud = s.substring(0,index);

            stud = new student(forStud);



            int indexPrev = index;
            index = s.indexOf("SessionNumber",indexPrev+1);
            String forSession;
            while(index != -1) {

                forSession=s.substring(indexPrev+13,index);
                session ses = new session(forSession);
                sessions.add(ses);

                indexPrev = index;
                index = s.indexOf("SessionNumber",indexPrev+1);
            }
            forSession=s.substring(indexPrev);
            session ses = new session(forSession);
            sessions.add(ses);

        }

        public void Display() {

            System.out.println(stud.firstName+" "+stud.secondName+" "+stud.fatherName+" "+stud.group+" "+stud.course);

            for(session session: sessions) {

                System.out.print("session:"+session.sessionNumber+"\n");

                for(SetoffBook.session.SubjectAndMark sam : session.SaM) {
                    System.out.println(sam.Subject+" "+sam.Mark);
                }

            }

        }

        public boolean isGoodStudent() {
            boolean isGood = true;
            for(session s:sessions) {
                for(session.SubjectAndMark sam: s.SaM) {
                    if(sam.Mark<9) {
                        isGood = false;
                        break;
                    }
                }
            }

            return isGood;
        }

        public void WriteToFile(String fileName) throws IOException {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName,true));
            printWriter.append("\n\n\n\n");
            printWriter.append("Фамилия :"+stud.firstName+" "+"Имя :"+stud.secondName+" "+"Отчество :"+stud.fatherName+" "+"Группа :"+stud.group+" "+"Курс :"+stud.course);
            printWriter.append("\n");

            for(session session: sessions) {

                printWriter.append("session:"+session.sessionNumber+"\n");

                for(SetoffBook.session.SubjectAndMark sam : session.SaM) {
                    printWriter.append(sam.Subject+" "+sam.Mark+"\n");
                }
                printWriter.append("\n");

            }

            printWriter.close();

        }
    }


    public static void main(String[] args) {

        String fileName="input.txt";



            //read from file
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Vector<String> lineVector=new Vector<>();

            int StudentNum=0;
            StudentNum=Integer.parseInt(reader.readLine());

            for(int i =0;i<StudentNum;i++) {
                lineVector.add(reader.readLine());
            }
            //

            List<SetoffBook> setoffBookList = new ArrayList<>();

            for(int i=0;i<StudentNum;i++) {
                SetoffBook setoffBook = new SetoffBook(lineVector.get(i));
                setoffBookList.add(setoffBook);
            }

            for( SetoffBook sob : setoffBookList) {
                sob.WriteToFile("output.txt");
            }

            for( SetoffBook sob : setoffBookList) {
                if(sob.isGoodStudent()){
                sob.WriteToFile("outputGood.txt");
                }
            }


        }
        catch (java.io.IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
}

