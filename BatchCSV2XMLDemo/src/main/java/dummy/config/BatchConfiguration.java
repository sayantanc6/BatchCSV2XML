package dummy.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import dummy.Student;
import dummy.StudentProcessor;
import dummy.SudentJobListener;

@Configuration 
public class BatchConfiguration {

	  @Autowired
	  private JobBuilderFactory jobBuilderFactory;
	
	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  
	  @Value("${file.input}") 
	  private String fileInput;
	 
	  @Bean
	  public Job CsvToXmlJob() {
	    return jobBuilderFactory.get("CsvToXmlJob")
	    		.incrementer(new RunIdIncrementer()).listener(listener())
	    		.flow(step1())
	    		.end().build();
	  }
	 
	  @Bean
	  public Step step1() {
	    return stepBuilderFactory.get("step1").<Student, Student>chunk(10).reader(reader())
	        .writer(writer()).processor(processor()).build();
	  }
	 
	  @Bean
	  public StudentProcessor processor() {
	    return new StudentProcessor();
	  }
	  
	  @Bean
	  public FlatFileItemReader<Student> reader() {
		  return new FlatFileItemReaderBuilder<Student>().name("coffeeItemReader")
		            .resource(new ClassPathResource(fileInput))
		            .delimited() 
		            .names(new String[] { "rollNo", "name", "department" })
		            .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
		                setTargetType(Student.class);
		             }})
		            .build();
	  }
	 
	  @Bean
	  public StaxEventItemWriter<Student> writer() {
	    StaxEventItemWriter<Student> writer = new StaxEventItemWriter<>();
	    writer.setResource(new FileSystemResource("student.xml")); 
	    writer.setMarshaller(studentUnmarshaller());
	    writer.setRootTagName("students");
	    return writer;
	  }
	 
	@Bean
	  public Jaxb2Marshaller studentUnmarshaller() {
		  Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
	    unMarshaller.setClassesToBeBound(Student.class); 
	    return unMarshaller;
	  }
	
	@Bean
	public JobExecutionListener listener() {
		return new SudentJobListener();
	}
}
