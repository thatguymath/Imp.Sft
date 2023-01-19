import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*; 

public class Assistencia extends JFrame implements ActionListener{ 
    Connection conex;
    Statement declara;
    String tabela = " TABLE ";
    String criar = " CREATE ";
    String pesquisar = " SELECT ";
    String inserir = " INSERT ";
    String remover = " DELETE ";
    String onde = " WHERE ";
    String pareceCom = " LIKE ";
    String de = " FROM ";
    String em = " INTO ";
    String valor = " VALUES ";
    String esp = " ";
    String apost = "'";


    void runDatabase() {
        try {
            Class.forName("org.hsql.jdbcDriver");
            conex = DriverManager.getConnection("jdbc:HypersonicSQL:bd", "sa", "");
            declara = conex.createStatement();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            declara.executeUpdate(criar + tabela + "ORD_SERVICO (CODIGO INTEGER, DATA VARCHAR(30), STATUS VARCHAR(30), ID_TEC INTEGER, SERIAL_ITEM INTEGER)");
            declara.executeUpdate(criar + tabela + "TECNICO (ID INTEGER, NOME VARCHAR(30), TURNO VARCHAR(30), ENDERECO VARCHAR(30), FUNCAO VARCHAR(30), DATA_CONTRATO VARCHAR(30))");
            declara.executeUpdate(criar + tabela + "ITEM (SERIAL INTEGER, QUANTIDADE INTEGER)");
            JOptionPane.showMessageDialog(null, "Suas tabelas foram criadas com sucesso!\n\nClique em 'OK' para prosseguir para as janelas de operação.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
        } catch (NullPointerException ex) {
        }
    }

    
    JPanel menuPanel = new JPanel(new GridLayout(3,1));
    JButton adicionarButton = new JButton("Nova Entrada");
    JButton gerenciarButton = new JButton("Gerenciar Funcionários");
    JButton pesquisaButton = new JButton("Pesquisar");
    
    public Assistencia() {
        super("Operações  |  Javaz - Serviços e Consertos de Hardware");
        
        setPreferredSize(new Dimension(1000,600));
        setLayout(new GridLayout(3,3));

        adicionarButton.setBorder(BorderFactory.createLineBorder(Color.white, 15));
        adicionarButton.addActionListener(this);
        menuPanel.add(adicionarButton);

        gerenciarButton.setBorder(BorderFactory.createLineBorder(Color.white, 15));
        gerenciarButton.addActionListener(this);
        menuPanel.add(gerenciarButton);

        pesquisaButton.setBorder(BorderFactory.createLineBorder(Color.white, 15));
        pesquisaButton.addActionListener(this);
        menuPanel.add(pesquisaButton);

        add(Box.createRigidArea(new Dimension(300, 150)));
        add(Box.createRigidArea(new Dimension(100, 150)));
        add(Box.createRigidArea(new Dimension(300, 150)));
        add(Box.createRigidArea(new Dimension(300, 150)));

        menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(menuPanel);
        add(Box.createRigidArea(new Dimension(300, 150)));
        add(Box.createRigidArea(new Dimension(300, 150)));
        add(Box.createRigidArea(new Dimension(100, 150)));
        add(Box.createRigidArea(new Dimension(300, 150)));

        runDatabase();
        pack();

        setVisible(true);
    }
    

    JFrame adicionarFrame = new JFrame("Operações  |  Javaz - Serviços e Consertos de Hardware");

    JPanel ordemServicoPanel = new JPanel(new GridLayout(14,3));
    JLabel codigoOrdemServicoLabel = new JLabel("Código da Ordem de Serviço");
    JTextField codigoOrdemServicoText = new JTextField(30);
    JLabel dataOrdemServicoLabel = new JLabel("Data de Entrada");                           
    JTextField dataOrdemServicoText = new JTextField();            
    JLabel statusOrdemServicolLabel = new JLabel("Status do Conserto");
    Choice statusOrdemServicolChoice = new Choice();   
    JLabel tecnicoOrdemServicoLabel = new JLabel("ID do Técnico");                           
    JTextField tecnicoOrdemServicoText = new JTextField();
    JLabel serialItemLabel = new JLabel("Serial do Item");                           
    JTextField serialItemText = new JTextField();
    JButton addOrdemServico = new JButton("Adicionar");
    JButton atualizarOrdemServico = new JButton("Atualizar");
    
    JPanel itemPanel = new JPanel(new GridLayout(14,3));
    JLabel serialLabel= new JLabel("Serial do Item");  
    JTextField serialField= new JTextField(30);
    JLabel quantidadeItem = new JLabel("Quantidade");
    JTextField quantidadeItemText= new JTextField(30);
    JButton addItem = new JButton("Adicionar");
    JButton atualizarItem = new JButton("Atualizar");

    void adicionar () {
        addOrdemServico.addActionListener(inserirListener);
        addTecnico.addActionListener(inserirListener);
        addItem.addActionListener(inserirListener);
        atualizarOrdemServico.addActionListener(atualizarListener);
        atualizarTecnico.addActionListener(atualizarListener);
        atualizarItem.addActionListener(atualizarListener);
        adicionarFrame.setLayout(new GridLayout(1,5));

        ordemServicoPanel.add(codigoOrdemServicoLabel);
        ordemServicoPanel.add(codigoOrdemServicoText);
        ordemServicoPanel.add(dataOrdemServicoLabel);
        ordemServicoPanel.add(dataOrdemServicoText);
        ordemServicoPanel.add(statusOrdemServicolLabel);
        statusOrdemServicolChoice.add("Em conserto");
        statusOrdemServicolChoice.add("Finalizado");
        ordemServicoPanel.add(statusOrdemServicolChoice);   
        ordemServicoPanel.add(tecnicoOrdemServicoLabel);                       
        ordemServicoPanel.add(tecnicoOrdemServicoText);
        ordemServicoPanel.add(serialItemLabel);                       
        ordemServicoPanel.add(serialItemText);
        ordemServicoPanel.add(addOrdemServico);
        ordemServicoPanel.add(atualizarOrdemServico);
        ordemServicoPanel.setBorder(BorderFactory.createTitledBorder("Ordem de Servico"));
        adicionarFrame.add(ordemServicoPanel);

        itemPanel.add(serialLabel);
        itemPanel.add(serialField);
        itemPanel.add(quantidadeItem);
        itemPanel.add(quantidadeItemText);
        itemPanel.add(addItem);
        itemPanel.add(atualizarItem);
        itemPanel.setBorder(BorderFactory.createTitledBorder("Item"));
        adicionarFrame.add(itemPanel);

        adicionarFrame.pack();
        adicionarFrame.setVisible(true);
    }

    JFrame gerenciarFrame = new JFrame("Gerência  |  Javaz - Serviços e Consertos de Hardware");

    JPanel tecnicoPanel = new JPanel(new GridLayout(14,3));
    JLabel idLabel= new JLabel("ID");       
    JTextField idField= new JTextField(30);
    JLabel nomeTecnicoLabel = new JLabel("Nome");                           
    JTextField nomeTecnicoText = new JTextField();
    JLabel turno = new JLabel("Turno");                           
    Choice turnoChoice = new Choice();
    JLabel enderecoTecnicoLabel = new JLabel("Endereço");
    JTextField enderecoTecnicoText = new JTextField();
    JLabel cargoTecnicoLabel = new JLabel("Função");                           
    Choice cargoTecnicoChoice = new Choice();
    JLabel contratacaoDataTecnicoLabel = new JLabel("Data do Contrato");                           
    JTextField contratacaoDataTecnicoText = new JTextField(); 
    JButton addTecnico = new JButton("Adicionar");
    JButton atualizarTecnico = new JButton("Atualizar");

    void gerenciar () {
        addTecnico.addActionListener(inserirListener);
        atualizarTecnico.addActionListener(atualizarListener);
        gerenciarFrame.setLayout(new GridLayout(1,5));

        tecnicoPanel.add(idLabel);
        tecnicoPanel.add(idField);
        tecnicoPanel.add(nomeTecnicoLabel);
        tecnicoPanel.add(nomeTecnicoText);
        tecnicoPanel.add(turno);
        turnoChoice.add("Manhã");
        turnoChoice.add("Tarde");
        tecnicoPanel.add(turnoChoice);        
        tecnicoPanel.add(enderecoTecnicoLabel);
        tecnicoPanel.add(enderecoTecnicoText);                         
        tecnicoPanel.add(cargoTecnicoLabel);
        cargoTecnicoChoice.add("Analista");
        cargoTecnicoChoice.add("Supervisor");
        tecnicoPanel.add(cargoTecnicoChoice);                                              
        tecnicoPanel.add(contratacaoDataTecnicoLabel);
        tecnicoPanel.add(contratacaoDataTecnicoText);
        tecnicoPanel.add(addTecnico);
        tecnicoPanel.add(atualizarTecnico);
        tecnicoPanel.setBorder(BorderFactory.createTitledBorder("Técnico"));
        gerenciarFrame.add(tecnicoPanel);

        gerenciarFrame.pack();
        gerenciarFrame.setVisible(true);
    }


    JFrame pesquisaFrame = new JFrame("Pesquisa no BD  |  Javaz - Serviços e Consertos de Hardware");
    JPanel pesquisaFramePanel = new JPanel(new GridBagLayout());
    JLabel pesquisarThrLbl = new JLabel("Pesquisar na tabela: ");
    Choice pesquisarThrChc = new Choice();
    JLabel pesquisarByLbl = new JLabel("Buscando por resultados em: ");
    Choice pesquisarByChc = new Choice();
    JTextField campoPesquisaText = new JTextField();
    JButton procurarButton = new JButton("Pesquisar");
    JButton excluirButton = new JButton("Remover");
    JTextArea ocorrenciaPesquisa = new JTextArea(10,10);
    JScrollPane ocorrenciaPesquisaScrollPane = new JScrollPane(ocorrenciaPesquisa);
    
    void pesquisa(){
        pesquisaFrame.setLayout(new GridLayout(2,4));
        pesquisaFrame.setPreferredSize(new Dimension(550,310));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pesquisaFramePanel.add(pesquisarThrLbl, c);
        c.gridx = 1;
        c.gridy = 0;
        pesquisaFramePanel.add(pesquisarThrChc, c);
        c.gridx = 0;
        c.gridy = 1;
        pesquisaFramePanel.add(pesquisarByLbl, c);
        c.gridx = 1;
        c.gridy = 1;
        pesquisaFramePanel.add(pesquisarByChc, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pesquisaFramePanel.add(campoPesquisaText, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        pesquisaFramePanel.add(procurarButton, c);
        procurarButton.addActionListener(pesquisarListener);
        c.gridx = 1;
        c.gridy = 3;
        pesquisaFramePanel.add(excluirButton, c);
        excluirButton.addActionListener(excluirListener);

        pesquisaFramePanel.setBorder(BorderFactory.createTitledBorder("Pesquisa"));
        pesquisaFrame.add(pesquisaFramePanel);
        pesquisaFrame.add(ocorrenciaPesquisaScrollPane);
        ocorrenciaPesquisa.setEditable(false);
        pesquisarThrChc.add("ORD_SERVICO");
        pesquisarThrChc.add("TECNICO");
        pesquisarThrChc.add("ITEM");
        pesquisarByChc.add("CODIGO");
        pesquisarByChc.add("DATA");
        pesquisarByChc.add("STATUS");
        pesquisarByChc.add("ID_TEC");
        pesquisarByChc.add("SERIAL_ITEM");
        pesquisarThrChc.addItemListener(item);
        pesquisaFrame.pack();
        pesquisaFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adicionarButton) adicionar();
        if (e.getSource() == gerenciarButton) gerenciar();
        if (e.getSource() == pesquisaButton) pesquisa();
    }

    ItemListener item = new ItemListener () {
        public void itemStateChanged(ItemEvent evt){

            if(pesquisarThrChc.getSelectedItem() == "ORD_SERVICO"){
                pesquisarByChc.removeAll();
                pesquisarByChc.add("CODIGO");
                pesquisarByChc.add("DATA");
                pesquisarByChc.add("STATUS");
                pesquisarByChc.add("ID_TEC");
                pesquisarByChc.add("SERIAL_ITEM");

            } else if(pesquisarThrChc.getSelectedItem() == "TECNICO"){
                pesquisarByChc.removeAll();
                pesquisarByChc.add("ID");
                pesquisarByChc.add("NOME");
                pesquisarByChc.add("TURNO");
                pesquisarByChc.add("ENDERECO");
                pesquisarByChc.add("FUNCAO");
                pesquisarByChc.add("DATA_CONTRATO");

            } else if(pesquisarThrChc.getSelectedItem() == "ITEM"){
                pesquisarByChc.removeAll();
                pesquisarByChc.add("SERIAL");
                pesquisarByChc.add("QUANTIDADE");
            }
        }
    };

    ActionListener pesquisarListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt){
            PreparedStatement prepDeclara;
            ResultSet results;
            try {
                ocorrenciaPesquisa.setText("");
                prepDeclara = conex.prepareStatement(pesquisar + "*" + de + pesquisarThrChc.getSelectedItem() + onde + pesquisarByChc.getSelectedItem() + pareceCom + apost + campoPesquisaText.getText() + apost);
                results = prepDeclara.executeQuery();                    
                while (results.next()) {              
                    pesquisarByChc.getItemCount();

                    for(int i = 1; i <= pesquisarByChc.getItemCount(); i++){
                        ocorrenciaPesquisa.append(pesquisarByChc.getItem(i-1) + "=" + results.getString(i) + "\n");
                    }

                    ocorrenciaPesquisa.append("\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener excluirListener = new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            PreparedStatement prepDeclara;
            try {
                prepDeclara = conex.prepareStatement(remover + de + pesquisarThrChc.getSelectedItem() + onde + pesquisarByChc.getSelectedItem() + pareceCom + apost + campoPesquisaText.getText() + apost);
                prepDeclara.executeQuery();                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener inserirListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt){
            PreparedStatement prepDeclara;

            try {                   
                if (evt.getSource() == addTecnico) {
                    prepDeclara = conex.prepareStatement(pesquisar + "*" + de + "TECNICO" + onde + "ID" + pareceCom + apost + Integer.parseInt(idField.getText()) + apost);
                    ResultSet results = prepDeclara.executeQuery();

                    if ( results.next() ){ JOptionPane.showMessageDialog(null, "Este ID já cadastrado em um outro técnico!", "Aviso", JOptionPane.ERROR_MESSAGE); return; }

                    prepDeclara = conex.prepareStatement(inserir + em + "TECNICO" + valor + "(?, ?, ?, ?, ?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(idField.getText()));
                    prepDeclara.setString(2, nomeTecnicoText.getText());
                    prepDeclara.setString(3, turnoChoice.getSelectedItem());
                    prepDeclara.setString(4, enderecoTecnicoText.getText());
                    prepDeclara.setString(5, cargoTecnicoChoice.getSelectedItem());
                    prepDeclara.setString(6, contratacaoDataTecnicoText.getText());
                    prepDeclara.executeUpdate();

                } else if (evt.getSource() == addItem) {
                    prepDeclara = conex.prepareStatement(pesquisar + "*" + de + "ITEM" + onde + "SERIAL" + pareceCom + apost + Integer.parseInt(serialField.getText()) + apost);
                    ResultSet results = prepDeclara.executeQuery();

                    if ( results.next() ) { JOptionPane.showMessageDialog(null, "Este serial já foi cadastrado no sistema!", "Aviso", JOptionPane.ERROR_MESSAGE); return; }

                    prepDeclara = conex.prepareStatement(inserir + em + "ITEM" + valor + "(?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(serialField.getText()));
                    prepDeclara.setInt(2, Integer.parseInt(quantidadeItemText.getText()));
                    prepDeclara.executeUpdate();

                } else if (evt.getSource() == addOrdemServico) {
                    prepDeclara = conex.prepareStatement(pesquisar + "*" + de + "ORD_SERVICO" + onde + "CODIGO" + pareceCom + apost + Integer.parseInt(codigoOrdemServicoText.getText()) + apost);
                    ResultSet results = prepDeclara.executeQuery();

                    if ( results.next() ) { JOptionPane.showMessageDialog(null, "Esta Ordem de Servico já foi cadastrada no sistema!", "Aviso", JOptionPane.ERROR_MESSAGE); return; }   

                    prepDeclara = conex.prepareStatement(inserir + em + "ORD_SERVICO" + valor + "(?, ?, ?, ?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(codigoOrdemServicoText.getText()));
                    prepDeclara.setString(2, dataOrdemServicoText.getText());
                    prepDeclara.setString(3, statusOrdemServicolChoice.getSelectedItem());
                    prepDeclara.setString(4, tecnicoOrdemServicoText.getText());
                    prepDeclara.setString(5, serialItemText.getText());
                    prepDeclara.executeUpdate();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener atualizarListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt){
            PreparedStatement prepDeclara;
            try{
                if(evt.getSource() == atualizarOrdemServico){
                    prepDeclara = conex.prepareStatement(remover + de + "ORD_SERVICO" + onde + "CODIGO" + pareceCom + apost + Integer.parseInt(codigoOrdemServicoText.getText()) + apost);
                    prepDeclara.executeQuery();

                    prepDeclara = conex.prepareStatement(inserir + em + "ORD_SERVICO" + valor + "(?, ?, ?, ?, ?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(codigoOrdemServicoText.getText()));
                    prepDeclara.setString(2, dataOrdemServicoText.getText());
                    prepDeclara.setString(3, statusOrdemServicolChoice.getSelectedItem());
                    prepDeclara.setString(4, tecnicoOrdemServicoText.getText());
                    prepDeclara.setString(5, serialItemText.getText());
                    prepDeclara.executeUpdate();

                } else if(evt.getSource() == atualizarTecnico){
                    prepDeclara = conex.prepareStatement(remover + de + "TECNICO" + onde + "ID" + pareceCom + apost + Integer.parseInt(idField.getText()) + apost);
                    prepDeclara.executeQuery();

                    prepDeclara = conex.prepareStatement(inserir + em + "TECNICO" + valor + "(?, ?, ?, ?, ?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(idField.getText()));
                    prepDeclara.setString(2, nomeTecnicoText.getText());
                    prepDeclara.setString(3, turnoChoice.getSelectedItem());
                    prepDeclara.setString(4, enderecoTecnicoText.getText());
                    prepDeclara.setString(5, cargoTecnicoChoice.getSelectedItem());
                    prepDeclara.setString(6, contratacaoDataTecnicoText.getText());
                    prepDeclara.executeUpdate();

                } else if(evt.getSource() == atualizarItem){
                    prepDeclara = conex.prepareStatement(remover + de + "ITEM" + onde + "SERIAL" + pareceCom + apost + Integer.parseInt(serialField.getText()) + apost);
                    prepDeclara.executeQuery();   

                    prepDeclara = conex.prepareStatement(inserir + em + "ITEM" + valor + "(?, ?, ?)");
                    prepDeclara.setInt(1, Integer.parseInt(serialField.getText()));
                    prepDeclara.setInt(2, Integer.parseInt(quantidadeItemText.getText()));
                    prepDeclara.executeUpdate();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
}