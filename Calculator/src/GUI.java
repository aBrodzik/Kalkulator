import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    JFrame frame;
    JLabel label = new JLabel();
    String equation ="";
    JButton[] buttons = {
    new JButton("9"),
    new JButton("8"),
    new JButton("7"),
    new JButton("6"),
    new JButton("5"),
    new JButton("4"),
    new JButton("3"),
    new JButton("2"),
    new JButton("1"),
    new JButton("0"),
    new JButton("."),
    new JButton("="),
    new JButton("+"),
    new JButton("-"),
    new JButton("*"),
    new JButton("/"),
    new JButton("^"),
    new JButton("%")};

    public GUI() {

        this.frame = frame;
        this.frame = new JFrame();
        this.frame.setSize(400,400);
        this.frame.getContentPane().setBackground(Color.blue);

        formatButtons(this.frame, Color.darkGray, Color.white, 50, 50,50,50);

        label.setBounds(25,25,350,50);
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setForeground(Color.green);
        label.setText("Witaj w Kalkulatorze");
        this.frame.add(label);

        this.frame.setLayout(null);
        this.frame.setVisible(true);

    }

    void formatButtons(JFrame frame, Color back, Color front, int x, int y, int width, int height){

        //button position
        int buttonId =0;
        for (int i=0; i< 4;i++){    //dial
            for (int j=0 ; j<3; j++) {
                buttons[buttonId].setBounds(x + x * j, 2 * y + i * y, width, height);
                buttonId++;
            }
        }
        for (int i=0 ; i<4; i++){    // operators
            if(buttonId==buttons.length-1){
                break;
            }
            for (int j=0; j<3; j++){
                if(buttonId==buttons.length-1){
                    break;
                }
                buttons[buttonId].setBounds(4*x+10+50*j,2*y+i*50,width,height);
                buttonId++;
            }
        }

        //buttons formatting
        buttonId =0;

        for (int i=0; i< buttons.length;i++){
                buttons[buttonId].setBackground(back);
                buttons[buttonId].setForeground(front);
                int tempButtonId = buttonId;
            final Boolean[] answered = {false};
                buttons[buttonId].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (buttons[tempButtonId].getText().equals("=")){
                            equation = Program.simplify(equation);
                            label.setText(equation);
                            answered[0] = true;
                        }
                        else if(equation.length()<50){
                            if (answered[0]) {
                                equation = "";
                                label.setText(equation);
                                answered[0] =false;
                            }
                            equation = equation + buttons[tempButtonId].getText();
                            label.setText(equation);
                        }
                    }
                });
                frame.add(buttons[buttonId]);
                buttonId++;
            }
        }

public static void main (String[] args){
        new GUI();


    }



}
