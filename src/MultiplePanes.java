import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MultiplePanes extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton button_1 = new JButton("<<");	
	private JButton button_2 = new JButton(">>");
	private JButton pesquisa = new JButton("Pesquisa");
	private int page = 1;
	CarregaPdf carregaPDF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultiplePanes frame = new MultiplePanes();
					frame.setTitle("Conferência de Ponto");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MultiplePanes() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_1.setVisible(false);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 784, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 784, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel lblArquivoDoEmpregado = new JLabel("Selecione o arquivo do empregado:");
		
		textField = new JTextField();
		textField.setToolTipText("empregado alt+T");
		textField.setColumns(10);
		textField.setFocusAccelerator('T');
		
		JButton button = new JButton("...");
		button.setToolTipText("Pesquisar alt+O");
		button.setMnemonic(KeyEvent.VK_O);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = fileChooser.showOpenDialog(button);
				
				if(i!=1) {
					File arquivo = fileChooser.getSelectedFile();
					textField.setText(arquivo.getPath());
				}
				
			}
		});
		
		JButton btnCarregar = new JButton("Carregar");
		btnCarregar.setToolTipText("Carregar alt+C");
		btnCarregar.setMnemonic(KeyEvent.VK_C);
		
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					page = 1;
					panel_1.removeAll();
					carregaEmp(panel_1, textField.getText(), page);
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "Caminho de arquivo inválido, favor verificar.");
				}
				
			}
		});
		
		
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(page > 1) {
				page--;
				panel_1.removeAll();
				carregaEmp(panel_1, textField.getText(), page);
				}
				
			}
		});
		
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(page < carregaPDF.getNpag()) {
					page++;
					panel_1.removeAll();
					carregaEmp(panel_1, textField.getText(), page);
				}
				
			}
		});
		
		pesquisa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					
					String pesquisa = JOptionPane.showInputDialog(contentPane, "Digitar o nome a ser pesquisado");
					
					if(!pesquisa.isEmpty()) {
						
						int pagina = Utilitarios.getPage(textField.getText(), pesquisa);
						if(pagina!=0) {
							panel_1.removeAll();
							page = pagina;
							carregaEmp(panel_1, textField.getText(), page);	
						}
					}
				
			}
		});
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCarregar))
						.addComponent(lblArquivoDoEmpregado))
					.addContainerGap(294, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblArquivoDoEmpregado)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button)
						.addComponent(btnCarregar))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		
	}
	
	public void carregaEmp(JPanel panel_1, String path, int page) {
		
		carregaPDF = new CarregaPdf(path, page);
		
		PontoTableModel pontoTableModel = new PontoTableModel(carregaPDF.getPonto());
		
		JTable table = new JTable();
		table.setModel(pontoTableModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		Font font = new Font("Arial", Font.BOLD, 15);
		Font font_title = new Font("Arial", Font.BOLD, 25);
		Font font_plain = new Font("Arial", Font.PLAIN, 15);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		JLabel lblNewLabel_1 = new JLabel(carregaPDF.getNome());
		JLabel titulo = new JLabel("Conferência de Ponto");
		
		lblNewLabel.setFont(font);
		lblNewLabel_1.setFont(font_plain);
		titulo.setFont(font_title);
		
		JLabel lblHorasNoturnas = new JLabel("Horas Noturnas:");
		JLabel lblNewLabel_2 = new JLabel(TrataPonto.mudaString(carregaPDF.getTothrsnot()));
		lblHorasNoturnas.setFont(font);
		lblNewLabel_2.setFont(font_plain);
		
		JLabel lblHorasDiurnas = new JLabel("Horas diurnas:");
		JLabel lblNewLabel_3 = new JLabel(TrataPonto.mudaString(carregaPDF.getTothrsdia()));
		lblHorasDiurnas.setFont(font);
		lblNewLabel_3.setFont(font_plain);
		
		JLabel lblNumPag = new JLabel(page+" de "+carregaPDF.getNpag());
		lblNumPag.setFont(font_plain);
		
		
		if(carregaPDF.getNpag()<=1) {
			button_1.setEnabled(false);
			button_2.setEnabled(false);
			pesquisa.setVisible(false);
		}else {
			button_1.setEnabled(true);
			button_2.setEnabled(true);
			pesquisa.setVisible(true);
		}
		
		button_1.setMnemonic(KeyEvent.VK_V);
		button_1.setToolTipText("Voltar alt+v");
		button_2.setMnemonic(KeyEvent.VK_A);
		button_2.setToolTipText("Avançar alt+a");
		pesquisa.setMnemonic(KeyEvent.VK_P);
		pesquisa.setToolTipText("Pesquisar empregado alt+p");
		
		
		GroupLayout gl_panel1 = new GroupLayout(panel_1);
		gl_panel1.setHorizontalGroup(
				gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
							
						.addGroup(gl_panel1.createSequentialGroup()
								.addContainerGap()
								.addComponent(button_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNumPag)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(button_2)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pesquisa)
								.addContainerGap())
						
						.addGroup(gl_panel1.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1))
						.addGroup(gl_panel1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel1.createSequentialGroup()
									.addComponent(lblHorasDiurnas)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel_3))
								.addGroup(gl_panel1.createSequentialGroup()
									.addComponent(lblHorasNoturnas)
									.addGap(18)
									.addComponent(lblNewLabel_2))))
						.addGroup(gl_panel1.createSequentialGroup()
							.addGap(202)
							.addComponent(titulo))
						.addGroup(gl_panel1.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel1.setVerticalGroup(
				
				gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
							.addComponent(button_1)
							.addComponent(lblNumPag)
							.addComponent(button_2)
							.addComponent(pesquisa)))
				
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGap(7)
					.addComponent(titulo)
					.addGap(18)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHorasNoturnas)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHorasDiurnas)
						.addComponent(lblNewLabel_3))
					.addGap(25))
		);
		panel_1.setLayout(gl_panel1);
		
		panel_1.setVisible(true);
		
		
	}
}
