package com.example.ipldashboardnew.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.ipldashboardnew.data.MatchInput;
import com.example.ipldashboardnew.listener.JobCompletionNotificationListener;
import com.example.ipldashboardnew.model.Match;
import com.example.ipldashboardnew.processor.MatchProcessor;

@Configuration
public class MatchConfiguration {

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("matchInputReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(new String[] { "id",
                        "city",
                        "date",
                        "season",
                        "matchNumber",
                        "team1",
                        "team2",
                        "venue",
                        "tossWinner",
                        "tossDecision",
                        "superOver",
                        "winningTeam",
                        "wonBy",
                        "margin",
                        "method",
                        "playerOfMatch",
                        "team1Players",
                        "team2Players",
                        "umpire1",
                        "umpire2" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                        setTargetType(MatchInput.class);
                    }
                })
                .build();
    }

    @Bean
    public MatchProcessor processor() {
        return new MatchProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match (id, city,date,match_number,team1,team2,venue,toss_winner,toss_decision,super_over,winning_team,won_by,margin,player_of_match,umpire1,umpire2)"
                        + " VALUES (:id, :city , :date , :matchNumber , :team1, :team2, :venue, :tossWinner, :tossDecision, :superOver, :winningTeam, :wonBy, :margin, :playerOfMatch, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,
            JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Match> writer) {
        return new StepBuilder("step1", jobRepository)
                .<MatchInput, Match>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
