
insert into livro (titulo, autor, categoria, data_publicacao) VALUES
('Effective Java', 'Joshua Bloch', 'Programação', '2008-05-28'),
('Clean Code', 'Robert C. Martin', 'Programação', '2008-08-11'),
('Design Patterns', 'Erich Gamma', 'Engenharia de Software', '1994-10-21'),
('Refactoring', 'Martin Fowler', 'Programação', '1999-06-20'),
('The Pragmatic Programmer', 'Andrew Hunt', 'Programação', '1999-10-20'),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Algoritmos', '2009-07-31'),
('Code Complete', 'Steve McConnell', 'Programação', '2004-06-19'),
('The Mythical Man-Month', 'Frederick P. Brooks Jr.', 'Gestão de Projetos', '1975-01-01'),
('Head First Design Patterns', 'Eric Freeman', 'Engenharia de Software', '2004-10-25'),
('Patterns of Enterprise Application Architecture', 'Martin Fowler', 'Engenharia de Software', '2002-11-15');


insert into usuario (nome, email, data_cadastro, telefone) VALUES
('João Silva', 'joao.silva@example.com', '2023-01-15', '11987654321'),
('Maria Oliveira', 'maria.oliveira@example.com', '2023-02-10', '11987654322'),
('Pedro Santos', 'pedro.santos@example.com', '2023-03-05', '11987654323'),
('Ana Costa', 'ana.costa@example.com', '2023-04-20', '11987654324'),
('Lucas Pereira', 'lucas.pereira@example.com', '2023-05-18', '11987654325'),
('Carla Souza', 'carla.souza@example.com', '2023-06-15', '11987654326'),
('Fernanda Lima', 'fernanda.lima@example.com', '2023-07-01', '11987654327'),
('Rodrigo Mendes', 'rodrigo.mendes@example.com', '2023-08-10', '11987654328'),
('Juliana Rocha', 'juliana.rocha@example.com', '2023-09-05', '11987654329'),
('Ricardo Araujo', 'ricardo.araujo@example.com', '2023-10-12', '11987654330');



insert into emprestimo (usuario_id, livro_id, data_emprestimo, data_devolucao, status) VALUES
(1, 1, '2024-08-01', '2024-08-15', true),
(2, 3, '2024-08-05', '2024-08-19', true),
(3, 2, '2024-08-10', '2024-08-24', false),
(4, 4, '2024-07-20', '2024-08-03', true),
(5, 5, '2024-07-25', '2024-08-08', true),
(1, 2, '2024-06-15', '2024-06-29', false),
(2, 1, '2024-05-10', '2024-05-24', false),
(3, 3, '2024-08-12', '2024-08-26', true),
(4, 5, '2024-07-18', '2024-08-01', false),
(5, 4, '2024-06-20', '2024-07-04', false);

