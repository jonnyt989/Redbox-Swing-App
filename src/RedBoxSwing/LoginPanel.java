package RedBoxSwing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginPanel extends JPanel{

	private JLabel emailLabel;
	private JTextField emailField;
	private JButton loginBtn;
	private LoginListener loginListener;
	
	public LoginPanel(){
		Dimension dim = getPreferredSize();
		dim.width = 385; 
		setPreferredSize(dim);
		
		emailLabel = new JLabel("Email: ");
		emailField = new JTextField(18);
		
		loginBtn = new JButton("Login");
		
		loginBtn.addActionListener(new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				
				System.out.println("Email = " + email);
				
				LoginEvent ev = new LoginEvent(this, email);
				
				if(loginListener != null) {
					loginListener.loginEventOccurred(ev);
				}
			}	
		});
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//////////// Field row /////////////
		gc.gridy = 1;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(emailLabel, gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(emailField, gc);
		
		
		//////////// Button row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.BASELINE_LEADING;
		gc.insets = new Insets(0, 60, 0, 0);
		add(loginBtn, gc);	
	}
	
	public void setLoginListener(LoginListener listener){
		this.loginListener = listener;
	}
}
